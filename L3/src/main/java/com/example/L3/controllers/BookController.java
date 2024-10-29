package com.example.L3.controllers;

import com.example.L3.dto.CollectionBookDto;
import com.example.L3.dto.CreateBookDto;
import com.example.L3.dto.ReadBookDto;
import com.example.L3.dto.UpdateBookDto;
import com.example.L3.models.Book;
import com.example.L3.models.Library;
import com.example.L3.services.BookService;
import com.example.L3.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final LibraryService libraryService;

    @Autowired
    public BookController(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionBookDto>> getAllBooks() {
        var books = bookService.getAllBooks();

        var bookDtoCollection = books.stream()
                .map(CollectionBookDto::new)
                .toList();

        return new ResponseEntity<>(bookDtoCollection, HttpStatus.OK);
    }

    @GetMapping("/library/{libraryId}")
    public ResponseEntity<List<ReadBookDto>> getBooksByLibraryId(@PathVariable UUID libraryId) {
        var books = bookService.getBooksByLibraryId(libraryId);
        var bookDtoCollection = books.stream().map(ReadBookDto::new).toList();
        return bookDtoCollection.isEmpty()
                ? new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(bookDtoCollection, HttpStatus.OK);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<ReadBookDto> getBookById(@PathVariable UUID bookId) {
        var bookOptional = bookService.getBookById(bookId);
        return bookOptional.map(book -> new
                ResponseEntity<>(new ReadBookDto(book), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("library/{libraryId}")
    public ResponseEntity<ReadBookDto> createBook(@RequestBody CreateBookDto createBookDto, @PathVariable UUID libraryId) {
        var libraryOptional = libraryService.getLibraryById(libraryId);

        if (libraryOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Library library = libraryOptional.get();
        Book newBook = createBookDto.toBook(library);
        bookService.saveBook(newBook);

        return new ResponseEntity<>(new ReadBookDto(newBook), HttpStatus.CREATED);
    }

    @PutMapping("{bookId}")
    public ResponseEntity<ReadBookDto> updateBook(@PathVariable UUID bookId, @RequestBody UpdateBookDto updateBookDto) {
        var bookByIdOptional = bookService.getBookById(bookId);
        if (bookByIdOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var libraryByIdOptional = libraryService.getLibraryById(updateBookDto.getLibraryId());
        if (libraryByIdOptional.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var book = bookByIdOptional.get();
        var updatedBook = updateBookDto.toBook(libraryByIdOptional.get());

        bookService.updateBook(book.getId(), updatedBook);
        return new ResponseEntity<>(new ReadBookDto(updatedBook), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID bookId) {
        try {
            bookService.deleteBook(bookId);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }
}
