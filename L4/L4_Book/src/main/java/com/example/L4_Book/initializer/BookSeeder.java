package com.example.L4_Book.initializer;

import com.example.L4_Book.enums.Edition;
import com.example.L4_Book.models.Book;
import com.example.L4_Book.services.BookService;
import com.example.L4_Book.services.LibraryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

//@Component
public class BookSeeder {
    private final LibraryService libraryService;
    private final BookService bookService;

    public BookSeeder(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    //@PostConstruct
    public void seedData() {
        var libraries = libraryService.getAllLibraries();
        if (libraries.size() < 2) {
            System.out.println("Not enough libraries.");
            return;
        }

        var firstLibrary = libraries.getFirst();
        var lastLibrary = libraries.getLast();

        var book1 = Book.builder()
                .id(UUID.randomUUID())
                .title("Brave New World")
                .author("Aldous Huxley")
                .edition(Edition.FIRST_EDITION)
                .library(firstLibrary)
                .build();

        var book2 = Book.builder()
                .id(UUID.randomUUID())
                .title("1984")
                .author("George Orwell")
                .edition(Edition.SECOND_EDITION)
                .library(firstLibrary)
                .build();

        var book3 = Book.builder()
                .id(UUID.randomUUID())
                .title("The Catcher in the Rye")
                .author("J.D. Salinge")
                .edition(Edition.ANNIVERSARY_EDITION)
                .library(lastLibrary)
                .build();

        var book4 = Book.builder()
                .id(UUID.randomUUID())
                .title("Harry Potter and the Deathly Hallows Pt. 1")
                .author("J. K. Rowling")
                .edition(Edition.SPECIAL_EDITION)
                .library(lastLibrary)
                .build();

        bookService.save(book1);
        bookService.save(book2);
        bookService.save(book3);
        bookService.save(book4);

        System.out.println("Database seeded successfully.");
    }
}
