package com.alexander.springlibrary.service;

import com.alexander.springlibrary.entity.Library;
import com.alexander.springlibrary.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    // Получение всех библиотек
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    // Получение библиотеки по ID
    public Optional<Library> getLibraryById(Long id) {
        return libraryRepository.findById(id);
    }

    // Создание новой библиотеки
    public Library createLibrary(Library library) {
        return libraryRepository.save(library);
    }

    // Обновление библиотеки
    public Library updateLibrary(Long id, Library libraryDetails) {
        Optional<Library> existingLibrary = libraryRepository.findById(id);
        if (existingLibrary.isPresent()) {
            Library library = existingLibrary.get();
            library.setName(libraryDetails.getName());
            library.setBooks(libraryDetails.getBooks());
            return libraryRepository.save(library);
        }
        return null;
    }

    // Удаление библиотеки
    public boolean deleteLibrary(Long id) {
        if (libraryRepository.existsById(id)) {
            libraryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
