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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name="book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private List<Category> categories = new ArrayList<>();

    public Book(String title, Author author, Publisher publisher, List<Category> categories) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.categories = categories;
    }
}
