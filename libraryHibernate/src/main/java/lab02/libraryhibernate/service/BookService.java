package lab02.libraryhibernate.service;

import lab02.libraryhibernate.dao.AuthorDao;
import lab02.libraryhibernate.dao.BookDao;
import lab02.libraryhibernate.dao.CategoryDao;
import lab02.libraryhibernate.dao.PublisherDao;
import lab02.libraryhibernate.dtos.AuthorDto;
import lab02.libraryhibernate.dtos.BookDto;
import lab02.libraryhibernate.entities.Author;
import lab02.libraryhibernate.entities.Book;
import lab02.libraryhibernate.entities.Category;
import lab02.libraryhibernate.entities.Publisher;
import lab02.libraryhibernate.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final CategoryDao categoryDao;
    private final PublisherDao publisherDao;
    private final BookMapper bookMapper;

    public BookService(BookDao bookDao, AuthorDao authorDao, CategoryDao categoryDao, PublisherDao publisherDao, BookMapper bookMapper) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.categoryDao = categoryDao;
        this.publisherDao = publisherDao;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks(){
        List<Book> books = bookDao.getAllBooks();
        List<BookDto> BookDtos = new ArrayList<>();
        for (Book book : books){
            BookDtos.add(bookMapper.mapToDto(book));
        }
        return BookDtos;
    }
    public BookDto getBookById(Long id){
        Book book = bookDao.getBookById(id);
        if(book == null){
            return null;
        }
        return bookMapper.mapToDto(book);
    }

    public String updateBook(Long id, BookDto bookDto) {
        StringBuilder errorMessage = new StringBuilder();
        StringBuilder successMessage = new StringBuilder();

        Book book = bookDao.getBookById(id);
        if (book == null) {
            return "Book not found with ID: " + id;
        }


        if (bookDto.getTitle() != null && !bookDto.getTitle().isEmpty()) {
            book.setTitle(bookDto.getTitle());
        }


        if (bookDto.getAuthorId() != null && bookDto.getAuthorId() != 0) {
            Author author = authorDao.getAuthor(bookDto.getAuthorId());
            if (author == null) {
                errorMessage.append("Author not found, ");
            } else {
                book.setAuthor(author);
            }
        }

        // Обновление издателя, если указан
        if (bookDto.getPublisherId() != null && bookDto.getPublisherId() != 0) {
            Publisher publisher = publisherDao.getPublisher(bookDto.getPublisherId());
            if (publisher == null) {
                errorMessage.append("publisher not found, ");
            } else {
                book.setPublisher(publisher);
            }
        }

        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            List<Category> categories = new ArrayList<>();
            for (Long categoryId : bookDto.getCategoryIds()) {
                Category category = categoryDao.getCategory(categoryId);
                if (category == null) {
                    errorMessage.append("category not found with ID: ").append(categoryId).append(", ");
                }
                categories.add(category);
            }
                book.setCategories(categories);
        }

        if (!errorMessage.isEmpty()) {
            return errorMessage.append("cannot update book with ID ").append(id).append(". Please try again.").toString();
        }

        bookDao.updateBook(book);
        return successMessage.append("Book with ID ").append(id).append(" updated successfully").toString();
    }



    public String createBook(BookDto BookDto) {
        StringBuilder successMessage = new StringBuilder();
        StringBuilder errorMessage = new StringBuilder();

        Book book = new Book();
        book.setTitle(BookDto.getTitle());
        Author author = authorDao.getAuthor(BookDto.getAuthorId());
        if (author == null) {
            errorMessage.append("Author not found, ");
        }
        book.setAuthor(author);
        Publisher publisher = publisherDao.getPublisher(BookDto.getPublisherId());
        if (publisher == null) {
            errorMessage.append("publisher not found, ");
        }
        book.setPublisher(publisher);

        if(!BookDto.getCategoryIds().isEmpty()){
            List<Long> categoryIds = BookDto.getCategoryIds();
            List<Category> categories = new ArrayList<>();
            for (Long categoryId : categoryIds){
                Category category = categoryDao.getCategory(categoryId);
                if(category == null){
                    errorMessage.append("category not found, ");
                }
                categories.add(category);
            }
            book.setCategories(categories);
        }
        if(!errorMessage.isEmpty()){
            return errorMessage.append(" try again...").toString();
        }
        bookDao.createBook(book);
        return successMessage.append("Book created!").toString();
    }

    public void deleteBook(Long id){
        Book book = bookDao.getBookById(id);
        if(book != null){
            bookDao.deleteBook(book);
        }
    }

}
