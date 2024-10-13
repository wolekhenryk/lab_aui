package org.example;

import org.example.dto.BookDto;
import org.example.enums.Edition;
import org.example.models.Book;
import org.example.models.Library;

import java.io.*;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Book book1 = new Book.Builder()
                .setTitle("1984")
                .setAuthor("George Orwell")
                .setEdition(Edition.FIRST_EDITION)
                .build();

        Book book2 = new Book.Builder()
                .setTitle("Brave New World")
                .setAuthor("Aldous Huxley")
                .setEdition(Edition.SECOND_EDITION)
                .build();

        // Create books for the second library
        Book book3 = new Book.Builder()
                .setTitle("The Catcher in the Rye")
                .setAuthor("J.D. Salinger")
                .setEdition(Edition.FIRST_EDITION)
                .build();

        Book book4 = new Book.Builder()
                .setTitle("To Kill a Mockingbird")
                .setAuthor("Harper Lee")
                .setEdition(Edition.LIMITED_EDITION)
                .build();

        // Create additional books for the third library
        Book book5 = new Book.Builder()
                .setTitle("Pride and Prejudice")
                .setAuthor("Jane Austen")
                .setEdition(Edition.ANNIVERSARY_EDITION)
                .build();

        Book book6 = new Book.Builder()
                .setTitle("Moby Dick")
                .setAuthor("Herman Melville")
                .setEdition(Edition.THIRD_EDITION)
                .build();

        Book book7 = new Book.Builder()
                .setTitle("War and Peace")
                .setAuthor("Leo Tolstoy")
                .setEdition(Edition.FIRST_EDITION)
                .build();

        // Create the first library and add books
        Library library1 = new Library.Builder()
                .setName("City Library")
                .setManagerName("Alice Johnson")
                .addBook(book1)
                .addBook(book2)
                .build();

        // Link the books to the first library
        book1.setLibrary(library1);
        book2.setLibrary(library1);

        // Create the second library and add books
        Library library2 = new Library.Builder()
                .setName("Central Library")
                .setManagerName("Bob Smith")
                .addBook(book3)
                .addBook(book4)
                .build();

        // Link the books to the second library
        book3.setLibrary(library2);
        book4.setLibrary(library2);

        // Create the third library and add additional books
        Library library3 = new Library.Builder()
                .setName("Grand Library")
                .setManagerName("Emily Davis")
                .addBook(book5)
                .addBook(book6)
                .addBook(book7)
                .build();

        // Link the books to the third library
        book5.setLibrary(library3);
        book6.setLibrary(library3);
        book7.setLibrary(library3);

        // Ex. 2
        System.out.println("\n2.\n");
        var libraries = Stream.of(library1, library2, library3);

        libraries.forEach(lib -> {
            System.out.println(lib);
            lib.getBooks().forEach(book -> System.out.println('\t' + book.toString()));
        });

        // Ex. 3
        System.out.println("\n\n 3. \n");
        var allBooksAsSet = Stream.of(library1, library2, library3)
                .flatMap(lib -> lib.getBooks().stream())
                .collect(Collectors.toSet());

        allBooksAsSet.forEach(System.out::println);

        // Ex. 4
        System.out.println("\n\n4. \n");
        allBooksAsSet
                .stream()
                .filter(book -> book.getTitle().startsWith("T"))
                .sorted(Comparator.comparing(Book::getEdition))
                .forEach(System.out::println);

        // Ex. 5
        System.out.println("\n\n5. \n");
        var sortedDtoCollection = allBooksAsSet
                .stream()
                .map(BookDto::new)
                .sorted((b1, b2) -> {
                    var titleComparison = b1.getTitle().compareTo(b2.getTitle());
                    if (titleComparison != 0)
                        return titleComparison;

                    var authorComparison = b1.getAuthor().compareTo(b2.getAuthor());
                    if (authorComparison != 0)
                        return authorComparison;

                    var editionComparison = b1.getEdition().compareTo(b2.getEdition());
                    if (editionComparison != 0)
                        return editionComparison;

                    return b1.getLibraryName().compareTo(b2.getLibraryName());
                })
                .toList();

        sortedDtoCollection.forEach(System.out::println);

        // Ex. 6
        System.out.println("\n\n6. \n");
        // Serialize the libraries to a binary file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("libraries.bin"))) {
            oos.writeObject(Set.of(library1, library2, library3));
            System.out.println("Libraries have been serialized to 'libraries.bin'.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize the libraries from the binary file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("libraries.bin"))) {
            Set<Library> deserializedLibraries = (Set<Library>) ois.readObject();
            System.out.println("Libraries have been deserialized from 'libraries.bin'.");
            deserializedLibraries.forEach(library -> {
                System.out.println(library);
                library.getBooks().forEach(book -> System.out.println("\t" + book));
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Ex. 7
        System.out.println("\n\n7. \n");

        var allLibraries = Set.of(library1, library2, library3);
        var poolSize = 3;

        var customThreadPool = new ForkJoinPool(poolSize);

        try {
            customThreadPool.submit(() -> {
                // Use parallelStream and assign tasks to custom thread pool
                allLibraries
                        .parallelStream()
                        .forEach(library -> {
                            System.out.println(Thread.currentThread().getName() + " is processing " + library.getName());
                            library.getBooks().forEach(book -> {
                                try {
                                    // Simulate workload with sleep
                                    Thread.sleep(1000); // Simulates 1 second of workload per book
                                    System.out.println(Thread.currentThread().getName() + " processed book: " + book);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                        });
            }).get();  // Blocks until all tasks are finished
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            customThreadPool.shutdown(); // Shutdown the pool after task completion
            customThreadPool.awaitTermination(1, TimeUnit.MINUTES);  // Wait for all tasks to complete
        }

        System.out.println("All tasks completed.");
    }
}