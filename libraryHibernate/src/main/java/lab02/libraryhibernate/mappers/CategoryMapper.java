package lab02.libraryhibernate.mappers;

import lab02.libraryhibernate.dtos.CategoryDto;
import lab02.libraryhibernate.entities.Book;
import lab02.libraryhibernate.entities.Category;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {

    public CategoryDto mapToDto(Category category) {
        return new CategoryDto(category.getId(), category.getName(), getBooksIds(category));
    }

    public Category mapToEntity(CategoryDto categoryDto ) {
        return new Category(categoryDto.getName());
    }

    private List<Long> getBooksIds(Category category) {
        List<Book> books = category.getBooks();
        List<Long> ids = new ArrayList<>();
        for (Book book : books) {
            ids.add(book.getId());
        }
        return ids;
    }


}
