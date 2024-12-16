package com.example.L4_Book.repos;

import com.example.L4_Book.models.Book;
import com.example.L4_Book.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByLibrary(Library library);
    List<Book> findByLibraryId(UUID id);
}
