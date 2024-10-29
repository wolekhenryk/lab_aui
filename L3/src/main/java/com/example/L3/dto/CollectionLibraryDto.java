package com.example.L3.dto;

import com.example.L3.models.Library;

import java.util.UUID;

public class CollectionLibraryDto {
    private UUID id;
    private String name;

    public CollectionLibraryDto(Library library) {
        this.id = library.getId();
        this.name = library.getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
