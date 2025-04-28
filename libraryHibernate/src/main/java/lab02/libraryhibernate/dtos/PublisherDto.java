package lab02.libraryhibernate.dtos;

import jakarta.persistence.*;
import lab02.libraryhibernate.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {

    /*private Long Id;*/

    private String name;

    private Set<Long> books = new HashSet<>();

}
