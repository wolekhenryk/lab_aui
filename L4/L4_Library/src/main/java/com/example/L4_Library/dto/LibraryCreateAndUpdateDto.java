package com.example.L4_Library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LibraryCreateAndUpdateDto {
    private String name;
    private String managerName;
}
