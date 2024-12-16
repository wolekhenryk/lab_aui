package com.example.L4_Book.models;

import com.example.L4_Book.enums.Edition;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)") // UUID in binary format
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "edition", nullable = false)
    private Edition edition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "library_id", referencedColumnName = "id") // Foreign key to library
    private Library library;
}
