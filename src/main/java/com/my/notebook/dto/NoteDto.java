package com.my.notebook.dto;

import java.io.Serializable;

public class NoteDto implements Serializable {

    public NoteDto() {

    }

    public NoteDto(String id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    private String id;

    private String title;

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
