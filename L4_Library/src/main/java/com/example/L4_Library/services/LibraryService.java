package com.example.L4_Library.services;

import com.example.L4_Library.models.Library;
import com.example.L4_Library.repositories.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {
    private final LibraryRepository repository;

    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public List<Library> getAllLibraries() {
        return repository.findAll();
    }

    public Optional<Library> getById(UUID id) {
        return repository.findById(id);
    }

    public void save(Library library) {
        repository.save(library);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
