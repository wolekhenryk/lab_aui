package com.example.L4_Library.initializers;

import com.example.L4_Library.models.Library;
import com.example.L4_Library.services.LibraryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class Initializer {
    private final LibraryService service;
    private final RestTemplate template;

    public Initializer(LibraryService service, RestTemplate template) {
        this.service = service;
        this.template = template;
    }

    @PostConstruct
    public void seedData() {
        if (!service.getAllLibraries().isEmpty()) {
            System.out.println("Database is not empty - seeding skipped.");
            return;
        }

        var cityLibrary = Library.builder()
                .id(UUID.randomUUID())
                .name("City Library")
                .managerName("John Doe")
                .build();

        var centralLibrary = Library.builder()
                .id(UUID.randomUUID())
                .name("Central Library")
                .managerName("John Smith")
                .build();

        service.save(cityLibrary);
        service.save(centralLibrary);

        // Notify another microservice
        notifyMicroService(cityLibrary);
        notifyMicroService(centralLibrary);
    }

    private void notifyMicroService(Library library) {
        try {
            template.postForEntity("http://localhost:5001/api/books/libraries", library, Void.class);
            System.out.println("Poprawnie wysłano obiekt.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
