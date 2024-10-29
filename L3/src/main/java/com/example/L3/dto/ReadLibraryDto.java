package com.example.L3.dto;

import com.example.L3.models.Library;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadLibraryDto {
    private UUID id;
    private String name;
    private String managerName;

    public ReadLibraryDto(Library library) {
        this.id = library.getId();
        this.name = library.getName();
        this.managerName = library.getManagerName();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    @Override
    public String toString() {
        return "LibraryDto{" +
                "name='" + name + '\'' +
                ", managerName='" + managerName + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}