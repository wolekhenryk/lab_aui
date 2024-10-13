package com.example.L2.services;

import com.example.L2.models.Book;
import com.example.L2.models.Library;
import com.example.L2.repos.BookRepository;
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
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get books by their associated library
    public List<Book> getBooksByLibrary(Library library) {
        return bookRepository.findByLibrary(library);
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

    // Delete a book by its UUID
    public void deleteBook(UUID bookId) {
        bookRepository.deleteById(bookId);
    }
}
