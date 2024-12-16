package com.example.L4_Library.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "libraries")
public class Library {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)") // UUID in binary format
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "manager_name", nullable = false)
    private String managerName;
}
