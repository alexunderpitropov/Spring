package lab02.libraryhibernate.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "publisher", orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public Publisher(String name) {
        this.name = name;
        this.books = new HashSet<>();
    }
}
