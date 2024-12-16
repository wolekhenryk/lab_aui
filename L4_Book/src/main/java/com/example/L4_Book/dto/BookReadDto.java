package com.example.L4_Book.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BookReadDto {
    private UUID id;
    private String title;
    private String author;
    private String libraryName;
    private String edition;
}
