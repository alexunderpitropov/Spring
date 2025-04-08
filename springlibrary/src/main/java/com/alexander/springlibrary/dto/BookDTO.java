package com.alexander.springlibrary.dto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private Long publisherId;
    private List<Long> categoryIds;
}
