package com.example.L3.dto;

import com.example.L3.enums.Edition;
import com.example.L3.models.Book;
import com.example.L3.models.Library;

import java.util.UUID;

public class UpdateBookDto {
    private String title;
    private String author;
    private Edition edition;
    private UUID libraryId;

    // Constructors
    public UpdateBookDto() {}

    // Convert UpdateBookDto to a Book object, setting the Library association
    public Book toBook(Library library) {
        return new Book.Builder()
                .setTitle(this.title)
                .setAuthor(this.author)
                .setEdition(this.edition)
                .setLibrary(library)
                .build();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public String toString() {
        return "UpdateBookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition=" + edition +
                ", libraryId=" + libraryId +
                '}';
    }
}

