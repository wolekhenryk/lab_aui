package com.example.L3.dto;

import com.example.L3.enums.Edition;
import com.example.L3.models.Book;
import com.example.L3.models.Library;

import java.util.Objects;
import java.util.UUID;

public class CreateBookDto {
    private String title;
    private String author;
    private Edition edition;

    public CreateBookDto(String title, String author, Edition edition, UUID libraryId) {
        this.title = title;
        this.author = author;
        this.edition = edition;
    }

    public Book toBook(Library library) {
        return new Book.Builder()
                .setTitle(this.title)
                .setAuthor(this.author)
                .setEdition(this.edition)
                .setLibrary(library)
                .build();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBookDto that = (CreateBookDto) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && edition == that.edition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, edition);
    }
}
