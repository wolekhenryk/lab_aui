package com.example.L4_Book.controllers;

import com.example.L4_Book.dto.BookReadDto;
import com.example.L4_Book.models.Library;
import com.example.L4_Book.services.BookService;
import com.example.L4_Book.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books/libraries")
public class LibraryController {
    private final LibraryService libraryService;
    private final BookService bookService;

    @Autowired
    public LibraryController(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Void> addLibrary(@RequestBody Library library) {
        if (libraryService.getLibraryById(library.getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            libraryService.save(library);
            System.out.println("Saved library.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @GetMapping("{libraryId}")
    public ResponseEntity<List<BookReadDto>> getAllBooksFromLibrary(@PathVariable UUID libraryId) {
        var libraryResult = libraryService.getLibraryById(libraryId);
        if (libraryResult.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var result = bookService.findByLibrary(libraryResult.get())
                .stream()
                .map(book -> BookReadDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .libraryName(book.getLibrary().getName())
                        .edition(book.getEdition().toString())
                        .build()
                ).toList();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("{libraryId}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable UUID libraryId) {
        var libraryResult = libraryService.getLibraryById(libraryId);
        if (libraryResult.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var booksFromLibrary = bookService.findByLibrary(libraryResult.get());
        booksFromLibrary.forEach(b -> bookService.deleteById(b.getId()));

        libraryService.deleteById(libraryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
