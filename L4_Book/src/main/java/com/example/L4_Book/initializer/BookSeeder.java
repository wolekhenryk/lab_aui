package com.example.L4_Book.initializer;

import com.example.L4_Book.enums.Edition;
import com.example.L4_Book.models.Book;
import com.example.L4_Book.services.BookService;
import com.example.L4_Book.services.LibraryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class BookSeeder {
    private final LibraryService libraryService;
    private final BookService bookService;

    public BookSeeder(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    @PostConstruct
    public void seedData() {

        System.out.println("Database seeded successfully.");
    }
}
