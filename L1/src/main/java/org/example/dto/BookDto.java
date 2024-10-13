package org.example.dto;

import org.example.enums.Edition;
import org.example.models.Book;

public class BookDto {
    private String title;
    private String author;
    private String edition;
    private String libraryName;

    // Constructor
    public BookDto(String title, String author, String edition, String libraryName) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.libraryName = libraryName;
    }

    public BookDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.edition = book.getEdition().toString();
        this.libraryName = book.getLibrary().getName();
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition=" + edition +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}

