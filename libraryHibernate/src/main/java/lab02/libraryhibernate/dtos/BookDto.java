package lab02.libraryhibernate.dtos;

import jakarta.persistence.*;
import lab02.libraryhibernate.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private Long authorId;
    private Long publisherId;
    private List<Long> categoryIds = new ArrayList<>();

}
