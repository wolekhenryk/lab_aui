package com.example.L3.services;

import com.example.L3.models.Book;
import com.example.L3.models.Library;
import com.example.L3.repos.BookRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create or save a new book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Get a book by its UUID
    public Optional<Book> getBookById(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    // Get all books
    @Transactional
    public List<Book> getAllBooks() {
        var books = bookRepository.findAll();
        books.forEach(b -> Hibernate.initialize(b.getLibrary()));
        return books;
    }

    // Get books by their associated library
    @Transactional
    public List<Book> getBooksByLibrary(Library library) {
        var books = bookRepository.findByLibrary(library);
        books.forEach(b -> Hibernate.initialize(b.getLibrary()));
        return books;
    }

    // Update an existing book
    public Optional<Book> updateBook(UUID bookId, Book updatedBook) {
        return bookRepository.findById(bookId).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setEdition(updatedBook.getEdition());
            return bookRepository.save(existingBook);
        });
    }

    @Transactional
    public List<Book> getBooksByLibraryId(UUID libraryId) {
        var books = bookRepository.findByLibrary_Id(libraryId);
        books.forEach(b -> Hibernate.initialize(b.getLibrary())); // Initialize library if needed
        return books;
    }

    // Delete a book by its UUID
    public void deleteBook(UUID bookId) {
        bookRepository.deleteById(bookId);
    }
}
