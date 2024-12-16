package com.example.L4_Book.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString(exclude = "books")
@Entity
@Table(name = "libraries")
public class Library {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)") // UUID in binary format
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manager_name", nullable = false)
    private String managerName;

    @OneToMany(mappedBy = "library")
    private Set<Book> books = new HashSet<>();
}
