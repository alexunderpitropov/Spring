package lab02.libraryhibernate.mappers;

import lab02.libraryhibernate.dtos.AuthorDto;
import lab02.libraryhibernate.entities.Author;
import lab02.libraryhibernate.entities.Book;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorMapper {

    public AuthorDto mapToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), getBooksIds(author));
    }

    public Author mapToEntity(AuthorDto authorDto) {
        return new Author(authorDto.getName());
    }

    private Set<Long> getBooksIds(Author author) {
        Set<Book> books = author.getBooks();
        Set<Long> ids = new HashSet<>();
        for (Book book : books) {
            ids.add(book.getId());
        }
        return ids;
    }


}
