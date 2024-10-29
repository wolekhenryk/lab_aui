package com.example.L3.dto;

import com.example.L3.models.Book;

import java.util.UUID;

public class CollectionBookDto {
    private UUID id;
    private String title;

    public CollectionBookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
