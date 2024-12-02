package com.example.L4_Book.services;

import com.example.L4_Book.models.Library;
import com.example.L4_Book.repos.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {
    private final LibraryRepository repository;

    @Autowired
    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public List<Library> getAllLibraries() {
        return repository.findAll();
    }

    public Optional<Library> getLibraryById(UUID id) {
        return repository.findById(id);
    }

    public void save(Library library) {
        repository.save(library);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
