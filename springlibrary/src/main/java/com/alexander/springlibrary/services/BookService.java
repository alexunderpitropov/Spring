package com.alexander.springlibrary.service;

import com.alexander.springlibrary.entity.Book;
import com.alexander.springlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Получение всех книг
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Получение книги по ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Создание новой книги
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Обновление книги
    public Book updateBook(Long id, Book bookDetails) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setPublisher(bookDetails.getPublisher());
            book.setCategories(bookDetails.getCategories());
            return bookRepository.save(book);
        }
        return null;
    }

    // Удаление книги
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

