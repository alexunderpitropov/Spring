package lab.one.demo.services;

import lab.one.demo.dtos.BookDto;
import lab.one.demo.entities.Author;
import lab.one.demo.entities.Book;
import lab.one.demo.entities.Category;
import lab.one.demo.entities.Publisher;
import lab.one.demo.repository.AuthorRepository;
import lab.one.demo.repository.BookRepository;
import lab.one.demo.repository.CategoryRepository;
import lab.one.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;


    private BookDto mapToDto(Book book){
        List<Long> getCategoriesId = new ArrayList<>();
        for(Category cat : book.getCategories()){
            getCategoriesId.add(cat.getId());
        }
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(), getCategoriesId);
    }

    private Book mapToEntity(BookDto dto){
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new RuntimeException(""));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId()).orElseThrow(() -> new RuntimeException(""));
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());

        return new Book(dto.getTitle(), author, publisher, categories);
    }

    public List<BookDto> getAllBooks(){
        List<Book> allBooks = bookRepository.findAll();
        List<BookDto> dtos = new ArrayList<>();
        for(Book book : allBooks){
            dtos.add(mapToDto(book));
        }
        return dtos;
    }

    public BookDto getBookById(Long id){
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find this book"));

        return mapToDto(book);
    }

    public BookDto createBook(BookDto dto){
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new RuntimeException(""));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId()).orElseThrow(() -> new RuntimeException(""));
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());

        Book book = new Book(dto.getTitle(), author, publisher, categories);

        Book savedBook = bookRepository.save(book);

        author.getBooks().add(savedBook);
        authorRepository.save(author);

        publisher.getBooks().add(savedBook);
        publisherRepository.save(publisher);

        for(Category category : categories){
            category.getBooks().add(savedBook);
            categoryRepository.save(category);
        }
        return mapToDto(savedBook);
    }

    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot delete this book"));
        bookRepository.delete(book);
    }

    public BookDto updateBook(Long id, BookDto dto){
        Author author = authorRepository.findById(dto.getAuthorId()).orElseThrow(() -> new RuntimeException(" "));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId()).orElseThrow(() -> new RuntimeException(" "));
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException(" "));

        book.setTitle(dto.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        if(!categories.isEmpty()){
            book.setCategories(categories);
        }
        Book updated = bookRepository.save(book);
        return mapToDto(updated);
    }




}
