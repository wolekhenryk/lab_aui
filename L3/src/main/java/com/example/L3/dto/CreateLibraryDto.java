package com.example.L3.dto;

import com.example.L3.models.Library;

import java.util.Objects;

public class CreateLibraryDto {
    private String name;
    private String managerName;

    public CreateLibraryDto(String name, String managerName) {
        this.name = name;
        this.managerName = managerName;
    }

    public Library toLibrary() {
        return new Library.Builder()
                .setName(this.name)
                .setManagerName(this.managerName)
                .build();
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateLibraryDto that = (CreateLibraryDto) o;
        return Objects.equals(name, that.name) && Objects.equals(managerName, that.managerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, managerName);
    }
}
