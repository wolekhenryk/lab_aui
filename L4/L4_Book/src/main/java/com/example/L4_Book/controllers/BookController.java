package com.example.L4_Book.controllers;

import com.example.L4_Book.dto.BookCreateAndUpdateDto;
import com.example.L4_Book.dto.BookReadDto;
import com.example.L4_Book.models.Book;
import com.example.L4_Book.services.BookService;
import com.example.L4_Book.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<BookReadDto>> getAllBooks() {
        var allBooks = bookService
                .getAllBooks()
                .stream()
                .map(book -> BookReadDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .libraryName(book.getLibrary().getName())
                        .edition(book.getEdition().toString())
                        .build())
                .toList();

        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<BookReadDto> getBookById(@PathVariable UUID bookId) {
        return bookService
                .findBookById(bookId)
                .map(book -> BookReadDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .libraryName(book.getLibrary().getName())
                        .edition(book.getEdition().toString())
                        .build())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("library/{libraryId}")
    public ResponseEntity<BookReadDto> createBook(@PathVariable UUID libraryId, @RequestBody BookCreateAndUpdateDto bookCreate) {
        var libraryResult = libraryService.getLibraryById(libraryId);
        if (libraryResult.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var library = libraryResult.get();

        var newBook = Book.builder()
                .id(UUID.randomUUID())
                .library(library)
                .title(bookCreate.getTitle())
                .author(bookCreate.getAuthor())
                .edition(bookCreate.getEdition())
                .build();

        bookService.save(newBook);

        var newBookReadDto = BookReadDto.builder()
                .id(newBook.getId())
                .title(newBook.getTitle())
                .author(newBook.getAuthor())
                .edition(newBook.getEdition().toString())
                .libraryName(newBook.getLibrary().getName())
                .build();

        return new ResponseEntity<>(newBookReadDto, HttpStatus.CREATED);
    }

    @PutMapping("{bookId}")
    public ResponseEntity<BookReadDto> updateBook(@PathVariable UUID bookId, @RequestBody BookCreateAndUpdateDto bookUpdate) {
        var existingBook = bookService.findBookById(bookId);
        if (existingBook.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var book = existingBook.get();

        book.setTitle(bookUpdate.getTitle());
        book.setAuthor(bookUpdate.getAuthor());
        book.setEdition(bookUpdate.getEdition());

        bookService.save(book);

        var bookReadDto = BookReadDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .edition(book.getEdition().toString())
                .libraryName(book.getLibrary().getName())
                .build();

        return new ResponseEntity<>(bookReadDto, HttpStatus.OK);
    }

    @DeleteMapping("{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID bookId) {
        if (bookService.findBookById(bookId).isPresent()) {
            bookService.deleteById(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
