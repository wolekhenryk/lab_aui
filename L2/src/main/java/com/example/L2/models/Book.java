package com.example.L2.models;

import com.example.L2.enums.Edition;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book implements Comparable<Book>, Serializable {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)") // UUID in binary format
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "edition", nullable = false)
    private Edition edition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", referencedColumnName = "id") // Foreign key to library
    private Library library;

    // Private constructor that takes the Builder
    private Book(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.edition = builder.edition;
        this.library = builder.library;
        this.id = UUID.randomUUID();
    }

    public Book() {
        this.id = UUID.randomUUID(); // Generate UUID when creating a new library
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && edition == book.edition
                && Objects.equals(library, book.library);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, edition);
    }

    @Override
    public int compareTo(Book other) {
        // Compare by title first
        var titleComparison = this.title.compareTo(other.title);
        if (titleComparison != 0) {
            return titleComparison;
        }

        // If titles are the same, compare by author
        var authorComparison = this.author.compareTo(other.author);
        if (authorComparison != 0) {
            return authorComparison;
        }

        // If authors are the same, compare by edition
        return this.edition.compareTo(other.edition);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", edition=" + edition +
                (library != null ? ", library='" + library.getName() + '\'' : "") +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static class Builder {
        private String title;
        private String author;
        private Edition edition;
        private Library library;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setEdition(Edition edition) {
            this.edition = edition;
            return this;
        }

        public Builder setLibrary(Library library) {
            this.library = library;
            return this;
        }

        // Build method to create Book instance
        public Book build() {
            return new Book(this);
        }
    }
}
