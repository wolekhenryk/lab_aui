package com.example.L2.repos;

import com.example.L2.models.Book;
import com.example.L2.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    // Custom query method to find books by their library
    List<Book> findByLibrary(Library library);
}
