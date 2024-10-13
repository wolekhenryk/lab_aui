package com.example.L2.dto;

import com.example.L2.models.Library;

import java.util.Set;
import java.util.stream.Collectors;

public class LibraryDto {
    private String name;
    private String managerName;
    private Set<BookDto> books;

    // Constructor
    public LibraryDto(String name, String managerName, Set<BookDto> books) {
        this.name = name;
        this.managerName = managerName;
        this.books = books;
    }

    public LibraryDto(Library library) {
        this.name = library.getName();
        this.managerName = library.getManagerName();
        this.books = library.getBooks()
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toSet());
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Set<BookDto> getBooks() {
        return books;
    }

    public void setBooks(Set<BookDto> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "LibraryDto{" +
                "name='" + name + '\'' +
                ", managerName='" + managerName + '\'' +
                ", books=" + books.size() +
                '}';
    }
}