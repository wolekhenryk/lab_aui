package com.example.L4_Library.controllers;

import com.example.L4_Library.dto.LibraryCreateAndUpdateDto;
import com.example.L4_Library.dto.LibraryReadDto;
import com.example.L4_Library.models.Library;
import com.example.L4_Library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/libraries")
public class LibraryController {
    private final LibraryService service;
    private final RestTemplate template;

    @Autowired
    public LibraryController(LibraryService service, RestTemplate template) {
        this.service = service;
        this.template = template;
    }

    @GetMapping
    public ResponseEntity<List<LibraryReadDto>> getAllLibraries() {
        var allLibraries = service
                .getAllLibraries()
                .stream()
                .map(l -> LibraryReadDto.builder()
                        .id(l.getId())
                        .name(l.getName())
                        .build()
                )
                .collect(Collectors.toList());

        return new ResponseEntity<>(allLibraries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibraryById(@PathVariable UUID id) {
        var result = service.getById(id);

        return result.map(library -> new ResponseEntity<>(library, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LibraryReadDto> createLibrary(@RequestBody LibraryCreateAndUpdateDto libraryCreateDto) {
        var library = Library.builder()
                .id(UUID.randomUUID())
                .name(libraryCreateDto.getName())
                .managerName(libraryCreateDto.getManagerName())
                .build();

        service.save(library);

        // Notify other microservice
        try {
            template.postForEntity("http://localhost:5001/api/books/libraries", library, Void.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return new ResponseEntity<>(LibraryReadDto.builder()
                .id(library.getId())
                .name(library.getName()).build(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibraryReadDto> updateLibrary(@PathVariable UUID id, @RequestBody LibraryCreateAndUpdateDto libraryUpdateDro) {
        return service.getById(id)
                .map(l -> {
                    l.setName(libraryUpdateDro.getName());
                    l.setManagerName(libraryUpdateDro.getManagerName());

                    service.save(l);

                    return new ResponseEntity<>(LibraryReadDto.builder()
                            .id(l.getId())
                            .name(l.getName())
                            .build(), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{libraryId}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable UUID libraryId) {
        if (service.getById(libraryId).isPresent()) {
            service.deleteById(libraryId);

            try {
                template.delete("http://localhost:5001/api/books/libraries/{id}", libraryId);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
