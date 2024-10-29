package com.example.L3.controllers;

import com.example.L3.dto.CollectionLibraryDto;
import com.example.L3.dto.CreateLibraryDto;
import com.example.L3.dto.ReadLibraryDto;
import com.example.L3.dto.UpdateLibraryDto;
import com.example.L3.models.Library;
import com.example.L3.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {
    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<CollectionLibraryDto>> getAllLibraries() {
        var libraries = libraryService.getAllLibraries();

        var dtoCollection = libraries.stream()
                .map(CollectionLibraryDto::new)
                .toList();

        return new ResponseEntity<>(dtoCollection, HttpStatus.OK);
    }

    @GetMapping("{libraryId}")
    public ResponseEntity<ReadLibraryDto> getLibraryById(@PathVariable UUID libraryId) {
        var libraryOptional = libraryService.getLibraryById(libraryId);
        return libraryOptional.map(lib -> new
                ResponseEntity<>(new ReadLibraryDto(lib), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ReadLibraryDto> createLibrary(@RequestBody CreateLibraryDto createLibraryDto) {
        var library = createLibraryDto.toLibrary();
        libraryService.saveLibrary(library);
        return new ResponseEntity<>(new ReadLibraryDto(library), HttpStatus.CREATED);
    }

    @PutMapping("{libraryId}")
    public ResponseEntity<ReadLibraryDto> updateLibrary(@PathVariable UUID libraryId, @RequestBody UpdateLibraryDto updateLibraryDto) {
        return libraryService.updateLibrary(libraryId, updateLibraryDto).map(lib -> new
                ResponseEntity<>(new ReadLibraryDto(lib), HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{libraryId}")
    public ResponseEntity<String> deleteLibrary(@PathVariable UUID libraryId) {
        try {
            libraryService.deleteLibrary(libraryId);
            return new ResponseEntity<>("Library deleted successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Library not found", HttpStatus.NOT_FOUND);
        }
    }
}
