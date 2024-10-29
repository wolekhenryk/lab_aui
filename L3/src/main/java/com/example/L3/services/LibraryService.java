package com.example.L3.services;

import com.example.L3.dto.UpdateLibraryDto;
import com.example.L3.models.*;
import com.example.L3.repos.*;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public List<Library> getAllLibraries() {
        var libraries = libraryRepository.findAll();
        libraries.forEach(l -> Hibernate.initialize(l.getBooks()));
        return libraries;
    }

    // Fetch a library by its ID
    @Transactional
    public Optional<Library> getLibraryById(UUID libraryId) {
        var lib = libraryRepository.findById(libraryId).orElse(null);
        if (lib == null)
            return Optional.empty();

        Hibernate.initialize(lib.getBooks());
        return Optional.of(lib);
    }

    // Save a new library
    public void saveLibrary(Library library) {
        libraryRepository.save(library);
    }

    @Transactional
    public Optional<Library> updateLibrary(UUID libraryId, UpdateLibraryDto updateLibraryDto) {
        return libraryRepository.findById(libraryId).map(existingLib -> {
            existingLib.setName(updateLibraryDto.getName());
            existingLib.setManagerName(updateLibraryDto.getManagerName());
            return libraryRepository.save(existingLib);
        });
    }

    public void deleteLibrary(UUID uuid) {
        libraryRepository.deleteById(uuid);
    }
}
