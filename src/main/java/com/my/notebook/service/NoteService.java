package com.my.notebook.service;

import com.my.notebook.dto.CreateRequest;
import com.my.notebook.dto.DeleteRequest;
import com.my.notebook.dto.NoteDto;
import com.my.notebook.dto.SearchRequest;
import com.my.notebook.dto.SearchResponse;
import com.my.notebook.service.model.Note;
import com.my.notebook.service.repository.DatabaseService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NoteService {

    @Autowired
    private DatabaseService databaseService;


    public NoteDto get (String id) {
        Note dbNote = databaseService.findById(id);
        if (dbNote == null) {
            throw new ObjectNotFoundException(id, Note.NAME);
        }

        return new NoteDto(dbNote.getId(), dbNote.getTitle(), dbNote.getText());
    }


    public SearchResponse search (SearchRequest searchRequest) {
        int count = databaseService.count(searchRequest);
        if (count == 0) {
            return new SearchResponse();
        }

        List<Note> notes = databaseService.search(searchRequest);

        SearchResponse response = new SearchResponse();
        response.setItems(notes.stream().map(t -> new NoteDto(t.getId(), t.getTitle(), t.getText())).collect(Collectors.toList()));
        response.setCount(count);

        return response;
    }

    @Transactional
    public void add (CreateRequest createRequest) {
        if (createRequest == null || createRequest.getText() == null ||  createRequest.getText().length() == 0) {
            throw new IllegalArgumentException();
        }

        Note note = new Note();
        note.setId(UUID.randomUUID().toString());
        note.setTitle(createRequest.getTitle());
        note.setText(createRequest.getText());
        note.setCreateDate(new Date());

        databaseService.persist(note);
    }

    @Transactional
    public void delete (DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() == null || deleteRequest.getId().length() == 0) {
            throw new IllegalArgumentException();
        }

        Note note = databaseService.findById(deleteRequest.getId());
        if (note == null) {
            throw new ObjectNotFoundException(deleteRequest.getId(), Note.NAME);
        }

        databaseService.delete(note);
    }

}
