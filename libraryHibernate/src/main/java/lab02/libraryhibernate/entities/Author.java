package lab02.libraryhibernate.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lab02.libraryhibernate.entities.Book;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public Author(String name) {
        this.name = name;
        this.books = new HashSet<>();
    }
}
