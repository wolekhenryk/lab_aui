package com.example.L2.initializer;

import com.example.L2.models.Book;
import com.example.L2.models.Library;
import com.example.L2.enums.Edition;
import com.example.L2.services.BookService;
import com.example.L2.services.LibraryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final BookService bookService;
    private final LibraryService libraryService;

    // Constructor injection of services
    public DataInitializer(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
    }

    @PostConstruct
    public void initData() {
        // Create a new library with UUID auto-generated
        Library cityLibrary = new Library.Builder()
                .setName("City Library")
                .setManagerName("Alice Johnson")
                .build();

        Library centralLibrary = new Library.Builder()
                .setName("Central Library")
                .setManagerName("Bob Smith")
                .build();

        // Save libraries
        libraryService.saveLibrary(cityLibrary);
        libraryService.saveLibrary(centralLibrary);

        // Create books for City Library
        Book book1 = new Book.Builder()
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setEdition(Edition.FIRST_EDITION)
                .setLibrary(cityLibrary)
                .build();

        Book book2 = new Book.Builder()
                .setTitle("Brave New World")
                .setAuthor("Aldous Huxley")
                .setEdition(Edition.SECOND_EDITION)
                .setLibrary(cityLibrary)
                .build();

        // Add books to City Library and save them
        cityLibrary.addBook(book1);
        cityLibrary.addBook(book2);
        bookService.saveBook(book1);
        bookService.saveBook(book2);

        // Create books for Central Library
        Book book3 = new Book.Builder()
                .setTitle("The Catcher in the Rye")
                .setAuthor("J.D. Salinger")
                .setEdition(Edition.FIRST_EDITION)
                .setLibrary(centralLibrary)
                .build();

        Book book4 = new Book.Builder()
                .setTitle("To Kill a Mockingbird")
                .setAuthor("Harper Lee")
                .setEdition(Edition.LIMITED_EDITION)
                .setLibrary(centralLibrary)
                .build();

        // Add books to Central Library and save them
        centralLibrary.addBook(book3);
        centralLibrary.addBook(book4);
        bookService.saveBook(book3);
        bookService.saveBook(book4);

        System.out.println("Example data initialized.");
    }
}
