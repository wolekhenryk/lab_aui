package com.example.L4_Book.services;

import com.example.L4_Book.models.Book;
import com.example.L4_Book.models.Library;
import com.example.L4_Book.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> findBookById(UUID id) {
        return repository.findById(id);
    }

    public List<Book> findByLibrary(Library library) {
        return repository.findByLibrary(library);
    }

    public void save(Book book) {
        repository.save(book);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}