package com.my.notebook.service.repository;

import com.my.notebook.dto.SearchRequest;
import com.my.notebook.service.model.Note;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Transactional
public class DatabaseService {

    @PersistenceContext
    private EntityManager em;


    public Note findById (String id) {
        return em.find(Note.class, id);
    }

    public void persist (Note note) {
        em.persist(note);
        em.flush();
    }

    public void delete (Note note) {
        em.remove(note);
        em.flush();
    }

    public List<Note> search (SearchRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Note> criteriaQuery = cb.createQuery(Note.class);
        Root<Note> root = criteriaQuery.from(Note.class);
        List<Order> orderList = Arrays.asList(cb.desc(root.get("createDate")), cb.desc(root.get("id")));
        List<Predicate> predicates = getPredicate(searchRequest, root);
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{})).orderBy(orderList);

        TypedQuery<Note> listQuery = em.createQuery(criteriaQuery);
        listQuery.setFirstResult((searchRequest.getPage() - 1) * searchRequest.getItemsPerPage());
        listQuery.setMaxResults(searchRequest.getItemsPerPage());

        return listQuery.getResultList();
    }

    public int count (SearchRequest searchRequest) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Note> root = criteriaQuery.from(Note.class);
        List<Predicate> predicates = getPredicate(searchRequest, root);
        criteriaQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Long> listQuery = em.createQuery(criteriaQuery);

        return listQuery.getSingleResult().intValue();
    }


    private List<Predicate> getPredicate(SearchRequest searchRequest, Root<Note> root) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Predicate> wherePredicates = new ArrayList<>();
        if (searchRequest.getSearchString() != null && searchRequest.getSearchString().length() != 0) {
            String srchStr = "%"+ searchRequest.getSearchString().toUpperCase() + "%";
            Predicate namePredicate = cb.like(cb.upper(root.get("name")), srchStr);
            Predicate titlePredicate = cb.like(cb.upper(root.get("title")), srchStr);
            wherePredicates.add(cb.or(namePredicate, titlePredicate));
        }

        return wherePredicates;
    }

}
