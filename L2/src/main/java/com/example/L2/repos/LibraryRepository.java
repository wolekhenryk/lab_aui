package com.example.L2.repos;

import com.example.L2.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, UUID> {
    // Standard CRUD operations are inherited from JpaRepository
}
