package com.alexander.springlibrary.dto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO {
    private Long id;
    private String name;
    private List<Long> bookIds;
}
