package lab.one.demo.dtos;


import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lab.one.demo.entities.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PublisherDto {

    private Long id;

    private String name;

    private List<Long> booksIds = new ArrayList<>();

    public PublisherDto(Long id, String name, List<Long> ids) {
        this.id = id;
        this.name = name;
        this.booksIds = ids;
    }

    public PublisherDto() {
    }

}
