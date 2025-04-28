package lab02.libraryhibernate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Book> books = new ArrayList<>();

    public Category(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }


}
