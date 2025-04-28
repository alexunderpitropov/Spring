package lab02.libraryhibernate.mappers;

import lab02.libraryhibernate.dtos.AuthorDto;
import lab02.libraryhibernate.dtos.PublisherDto;
import lab02.libraryhibernate.entities.Author;
import lab02.libraryhibernate.entities.Book;
import lab02.libraryhibernate.entities.Publisher;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PublisherMapper {

    public PublisherDto mapToDto(Publisher publisher) {
        return new PublisherDto(/*publisher.getId(), */publisher.getName(), getBooksIds(publisher));
    }

    public Publisher mapToEntity(PublisherDto publisherDto) {
        return new Publisher(publisherDto.getName());
    }

    private Set<Long> getBooksIds(Publisher publisher) {
        Set<Book> books = publisher.getBooks();
        Set<Long> ids = new HashSet<>();
        for (Book book : books) {
            ids.add(book.getId());
        }
        return ids;
    }
}
