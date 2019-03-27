package com.my.notebook.rest;

import com.my.notebook.dto.CreateRequest;
import com.my.notebook.dto.DeleteRequest;
import com.my.notebook.dto.NoteDto;
import com.my.notebook.dto.SearchRequest;
import com.my.notebook.dto.SearchResponse;
import com.my.notebook.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

    @Autowired
    NoteService noteService;

    @GetMapping("/note/{id}")
    private NoteDto get(@PathVariable("id") String id) {
        return noteService.get(id);
    }

    @PostMapping("/note/search")
    private SearchResponse search(@RequestBody SearchRequest searchRequest) {
        return noteService.search(searchRequest);
    }

    @PutMapping("/note/delete")
    private void delete(@RequestBody DeleteRequest deleteRequest) {
        noteService.delete(deleteRequest);
    }

    @PostMapping("/note/add")
    private void add(@RequestBody CreateRequest createRequest) {
        noteService.add(createRequest);
    }
}
