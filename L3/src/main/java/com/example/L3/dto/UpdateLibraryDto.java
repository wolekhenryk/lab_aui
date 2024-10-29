package com.example.L3.dto;

import com.example.L3.models.Library;

public class UpdateLibraryDto {
    private String name;
    private String managerName;

    public Library toLibrary() {
        return new Library.Builder()
                .setName(this.name)
                .setManagerName(this.managerName)
                .build();
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
