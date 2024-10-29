package com.example.L3.repos;

import com.example.L3.models.Book;
import com.example.L3.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    // Custom query method to find books by their library
    List<Book> findByLibrary(Library library);
    List<Book> findByLibrary_Id(UUID libraryId);
}
