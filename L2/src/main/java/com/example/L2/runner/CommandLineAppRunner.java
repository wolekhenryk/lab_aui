package com.example.L2.runner;

import com.example.L2.models.Book;
import com.example.L2.models.Library;
import com.example.L2.services.BookService;
import com.example.L2.services.LibraryService;
import com.example.L2.enums.Edition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Component
public class CommandLineAppRunner implements CommandLineRunner {

    private final BookService bookService;
    private final LibraryService libraryService;
    private final Scanner scanner;

    public CommandLineAppRunner(BookService bookService, LibraryService libraryService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean running = true;

        System.out.println("Welcome to the Library Management System!");
        while (running) {
            printMenu();
            String command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "list libraries":
                    listLibraries();
                    break;
                case "list books":
                    listBooks();
                    break;
                case "add book":
                    addBook();
                    break;
                case "delete book":
                    deleteBook();
                    break;
                case "help":
                    printMenu();
                    break;
                case "exit":
                    running = false;
                    System.out.println("Stopping the application. Goodbye!");
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' to see available commands.");
            }
        }
    }

    // Print available commands
    private void printMenu() {
        System.out.println("\nAvailable commands:");
        System.out.println("1. list libraries - List all libraries");
        System.out.println("2. list books - List all books");
        System.out.println("3. add book - Add a new book");
        System.out.println("4. delete book - Delete an existing book");
        System.out.println("5. help - Show this menu");
        System.out.println("6. exit - Stop the application\n");
    }

    // List all libraries (categories)
    private void listLibraries() {
        List<Library> libraries = libraryService.getAllLibraries();
        if (libraries.isEmpty()) {
            System.out.println("No libraries available.");
        } else {
            libraries.forEach(library -> System.out.println(library.getId() + " - " + library.getName()));
        }
    }

    // List all books (elements)
    private void listBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(book -> System.out.println(book.getId() + " - " + book.getTitle() + " by " + book.getAuthor()));
        }
    }

    // Add a new book to a library
    private void addBook() {
        System.out.println("Enter the book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter the book author: ");
        String author = scanner.nextLine();
        System.out.println("Enter the edition (FIRST_EDITION, SECOND_EDITION, etc.): ");
        String editionStr = scanner.nextLine();
        Edition edition = Edition.valueOf(editionStr.toUpperCase());

        // List available libraries and let the user select one
        System.out.println("Select a library by its ID: ");
        listLibraries();
        String libraryIdStr = scanner.nextLine();
        UUID libraryId = UUID.fromString(libraryIdStr);
        Optional<Library> libraryOpt = Optional.ofNullable(libraryService.getLibraryById(libraryId));

        if (libraryOpt.isPresent()) {
            Library library = libraryOpt.get();
            Book newBook = new Book.Builder()
                    .setTitle(title)
                    .setAuthor(author)
                    .setEdition(edition)
                    .setLibrary(library)
                    .build();
            bookService.saveBook(newBook);
            System.out.println("Book added successfully to " + library.getName() + ".");
        } else {
            System.out.println("Invalid library ID.");
        }
    }

    // Delete an existing book by ID
    private void deleteBook() {
        System.out.println("Enter the ID of the book you want to delete: ");
        listBooks();
        String bookIdStr = scanner.nextLine();
        UUID bookId = UUID.fromString(bookIdStr);
        Optional<Book> bookOpt = bookService.getBookById(bookId);

        if (bookOpt.isPresent()) {
            bookService.deleteBook(bookId);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
}
