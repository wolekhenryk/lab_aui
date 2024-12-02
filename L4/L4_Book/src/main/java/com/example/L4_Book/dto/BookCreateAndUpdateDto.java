package com.example.L4_Book.dto;

import com.example.L4_Book.enums.Edition;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class BookCreateAndUpdateDto {
    private String title;
    private String author;
    private Edition edition;
}
