package com.alexander.springlibrary.service;

import com.alexander.springlibrary.entity.Author;
import com.alexander.springlibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    // Получение всех авторов
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Получение автора по ID
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    // Создание нового автора
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Обновление автора
    public Author updateAuthor(Long id, Author authorDetails) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setName(authorDetails.getName());
            author.setBiography(authorDetails.getBiography());
            return authorRepository.save(author);
        }
        return null;
    }

    // Удаление автора
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
