package lab.one.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AuthorDto {
    private Long id;

    private String name;

    private String surname;

    private List<Long> booksIds = new ArrayList<>();

    public AuthorDto() {
    }

    public AuthorDto(Long id, String name, String surname, List<Long> ids) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.booksIds = ids;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
