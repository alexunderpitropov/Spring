package lab02.libraryhibernate.service;

import lab02.libraryhibernate.dao.CategoryDao;
import lab02.libraryhibernate.dtos.CategoryDto;
import lab02.libraryhibernate.entities.Category;
import lab02.libraryhibernate.mappers.CategoryMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryMapper categoryMapper;


    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryDao.getAllCategories();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(categoryMapper.mapToDto(category));
        }
        return categoryDtos;
    }

    public CategoryDto getCategoryDto(Long id) {
        Category category = categoryDao.getCategory(id);
        if (category == null) {
            return null;
        }
        return categoryMapper.mapToDto(category);
    }

    public void createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToEntity(categoryDto);
        categoryDao.createCategory(category);
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryDao.getCategory(id);
        if(category != null){
            category.setName(categoryDto.getName());
            if(categoryDto.getBooksIds() != null){
                category.setBooks(categoryMapper.mapToEntity(categoryDto).getBooks());
            }
            Category updated = categoryDao.updateCategory(category);
            return categoryMapper.mapToDto(updated);
        }
        return null;
    }

    public void deleteCategory(Long id) {
        Category category = categoryDao.getCategory(id);
        categoryDao.deleteCategory(category);
    }
}
