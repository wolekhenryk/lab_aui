package com.example.L2.services;

import com.example.L2.models.*;
import com.example.L2.repos.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    // Fetch a library by its ID
    public Library getLibraryById(UUID libraryId) {
        return libraryRepository.findById(libraryId).orElse(null);
    }

    // Fetch books by a library
    public List<Book> getBooksByLibrary(Library library) {
        return bookRepository.findByLibrary(library);
    }

    // Save a new library
    public void saveLibrary(Library library) {
        libraryRepository.save(library);
    }

    // Save a new book
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
