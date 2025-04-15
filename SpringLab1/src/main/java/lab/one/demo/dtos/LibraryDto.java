package lab.one.demo.dtos;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class LibraryDto {
    private Long id;
    private String name;
    @ElementCollection
    private List<Long> books = new ArrayList<>();
    public LibraryDto() {
    }
    public LibraryDto(Long id, String name, List<Long> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
}
