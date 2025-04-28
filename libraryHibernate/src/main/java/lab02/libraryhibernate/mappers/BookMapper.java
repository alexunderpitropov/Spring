package lab02.libraryhibernate.mappers;

import lab02.libraryhibernate.dao.AuthorDao;
import lab02.libraryhibernate.dao.CategoryDao;
import lab02.libraryhibernate.dao.PublisherDao;
import lab02.libraryhibernate.dtos.BookDto;
import lab02.libraryhibernate.entities.Book;
import lab02.libraryhibernate.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class BookMapper {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private PublisherDao publisherDao;

    public BookDto mapToDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(), getIds(book));
    }

    public Book mapToEntity(BookDto dto) {
        return new Book(dto.getTitle(), authorDao.getAuthor(dto.getAuthorId()), publisherDao.getPublisher(dto.getPublisherId()), getCategories(dto));
    }

    private List<Long> getIds(Book book) {
        List<Category> categories = book.getCategories();
        List<Long> ids = new ArrayList<>();
        for (Category category : categories) {
            ids.add(category.getId());
        }
        return ids;
    }

    private List<Category> getCategories(BookDto dto) {
        List<Long> ids = dto.getCategoryIds();
        List<Category> categories = new ArrayList<>();
        for(Long id : ids) {
            categories.add(categoryDao.getCategory(id));
        }
        return categories;
    }


}
