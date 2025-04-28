package lab02.libraryhibernate.controllers;

import lab02.libraryhibernate.dtos.CategoryDto;
import lab02.libraryhibernate.entities.Category;
import lab02.libraryhibernate.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryDto(id);
        if (categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public void addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.getCategoryDto(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok("Category updated" + categoryDto.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryDto(id);
        if (categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body("Category " +categoryDto.getName() + " deleted successfully");
    }
}
