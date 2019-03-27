package com.my.notebook.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResponse implements Serializable {

    private List<NoteDto> items = new ArrayList<>();

    private int count = 0;

    public List<NoteDto> getItems() {
        return items;
    }

    public void setItems(List<NoteDto> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
