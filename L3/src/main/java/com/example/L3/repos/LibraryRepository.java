package com.example.L3.repos;

import com.example.L3.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, UUID> {
    // Standard CRUD operations are inherited from JpaRepository
}
