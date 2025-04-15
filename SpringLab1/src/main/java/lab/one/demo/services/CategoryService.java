package lab.one.demo.services;
import lab.one.demo.dtos.CategoryDto;
import lab.one.demo.entities.Book;
import lab.one.demo.entities.Category;
import lab.one.demo.repository.BookRepository;
import lab.one.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    private CategoryDto mapToDto(Category category){
        return new CategoryDto(category.getId(), category.getName(), getIds(category));
    }
    private Category mapToEntity(CategoryDto categoryDto){
        return new Category(categoryDto.getName());
    }

    private List<Long> getIds(Category category){
        List<Book> books = category.getBooks();
        List<Long> ids = new ArrayList<>();
        for(var book : books){
            ids.add(book.getId());
        }
        return ids;
    }

    public List<CategoryDto> getAll(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtos = new ArrayList<>();
        for(Category cat : categories){
            dtos.add(mapToDto(cat));
        }
        return dtos;
    }

    public CategoryDto getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" "));
        return mapToDto(category);
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category = mapToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    public CategoryDto updateCategory(Long id, CategoryDto dto){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" "));
        List<Book> books = bookRepository.findAllById(dto.getBooks());

        category.setName(dto.getName());
        if(!books.isEmpty()){
        category.setBooks(books);
        }
        Category updated = categoryRepository.save(category);
        return mapToDto(updated);
    }

    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" "));
        categoryRepository.delete(category);
    }

}
