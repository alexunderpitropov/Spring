package lab02.libraryhibernate.controllers;

import lab02.libraryhibernate.dtos.BookDto;
import lab02.libraryhibernate.entities.Book;
import lab02.libraryhibernate.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody BookDto bookDto) {
        String message = bookService.createBook(bookDto);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        BookDto bookDto = bookService.getBookById(id);
        if(bookDto == null){
            return ResponseEntity.status(404).body("Book not found");
        }
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().body("Book deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        String message = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok().body(message);
    }
}
