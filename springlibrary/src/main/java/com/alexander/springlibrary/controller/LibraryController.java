package com.alexander.springlibrary.controller;

import com.alexander.springlibrary.entity.Library;
import com.alexander.springlibrary.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libraries")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Получение списка всех библиотек
    @GetMapping
    public List<Library> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    // Получение библиотеки по ID
    @GetMapping("/{id}")
    public ResponseEntity<Library> getLibraryById(@PathVariable Long id) {
        Optional<Library> library = libraryService.getLibraryById(id);
        return library.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Создание новой библиотеки
    @PostMapping
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        Library createdLibrary = libraryService.createLibrary(library);
        return new ResponseEntity<>(createdLibrary, HttpStatus.CREATED);
    }

    // Обновление информации о библиотеке
    @PutMapping("/{id}")
    public ResponseEntity<Library> updateLibrary(@PathVariable Long id, @RequestBody Library libraryDetails) {
        Library updatedLibrary = libraryService.updateLibrary(id, libraryDetails);
        return updatedLibrary != null ? ResponseEntity.ok(updatedLibrary) : ResponseEntity.notFound().build();
    }

    // Удаление библиотеки
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrary(@PathVariable Long id) {
        return libraryService.deleteLibrary(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
