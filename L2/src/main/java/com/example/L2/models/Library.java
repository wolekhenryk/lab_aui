package com.example.L2.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "libraries")
public class Library implements Comparable<Library>, Serializable {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)") // UUID in binary format
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    // Constructor that takes the Builder
    private Library(Builder builder) {
        this.name = builder.name;
        this.managerName = builder.managerName;
        this.books = builder.books;
        this.id = UUID.randomUUID();
    }

    public Library() {
        this.id = UUID.randomUUID(); // Generate UUID when creating a new library
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(name, library.name)
                && Objects.equals(managerName, library.managerName)
                && Objects.equals(books, library.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, managerName, books);
    }

    @Override
    public int compareTo(Library other) {
        var nameComparison = this.name.compareToIgnoreCase(other.name);
        if (nameComparison != 0)
            return nameComparison;

        var managerNameComparison = this.managerName.compareToIgnoreCase(other.managerName);
        if (managerNameComparison != 0)
            return managerNameComparison;

        return Integer.compare(this.books.size(), other.books.size());
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", managerName='" + managerName + '\'' +
                ", books=" + books.size() +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static class Builder {
        private String name;
        private String managerName;
        private Set<Book> books = new HashSet<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setManagerName(String managerName) {
            this.managerName = managerName;
            return this;
        }

        public Builder setBooks(Set<Book> books) {
            this.books = books;
            return this;
        }

        public Builder addBook(Book book) {
            this.books.add(book);
            return this;
        }

        // Build method to create Library instance
        public Library build() {
            return new Library(this);
        }
    }
}
