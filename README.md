# Отчет по лабораторной работе №1

**Питропов Александр, группа I2302**

## Тема

Разработка Spring Boot приложения для управления библиотекой

## Суть задания

Разработать Spring Boot приложение, которое содержит 3 контроллера, 3 сервиса и 5 взаимосвязанных сущностей. Приложение должно поддерживать CRUD-операции через REST API, использовать JPA для работы с базой данных и работать с DTO, а не напрямую с entity.

### Сущности и связи

* Author: один автор может написать много книг (One-to-Many)
* Publisher: один издатель может издать много книг (One-to-Many)
* Book: каждая книга принадлежит одному автору, одному издателю, может быть связана с несколькими категориями (Many-to-One, Many-to-Many)
* Category: одна категория может быть связана с несколькими книгами (Many-to-Many)
* Library: содержит коллекцию книг (ElementCollection)

## Часть про Author

### AuthorController.java

**Пояснение:** контроллер обрабатывает HTTP-запросы, связанные с авторами. Он позволяет получить список всех авторов, одного конкретного автора по id, создать нового автора, обновить существующего и удалить автора.

```java
package lab.one.demo.controllers;

import lab.one.demo.dtos.AuthorDto;
import lab.one.demo.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors(){
        List<AuthorDto> authors = service.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(Long id){
        AuthorDto author = service.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        AuthorDto createdAuthor = service.createAuthor(authorDto);
        return ResponseEntity.ok(createdAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        service.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto){
        AuthorDto updated = service.updateAuthor(id, authorDto);
        return ResponseEntity.ok(updated);
    }
}
```

### AuthorService.java

**Пояснение:** сервисный слой, который содержит бизнес-логику. Здесь происходит преобразование между entity и DTO, работа с репозиторием авторов и книг, обновление информации об авторах и их книгах.

```java
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    private AuthorDto mapToDto(Author author){
        return new AuthorDto(author.getId(), author.getName(), author.getSurname(), getIds(author));
    }

    private List<Long> getIds(Author author){
        List<Book> books = author.getBooks();
        List<Long> ids = new ArrayList<>();
        for(var book : books){
            ids.add(book.getId());
        }
        return ids;
    }

    private Author mapToEntity(AuthorDto authorDto){
        return new Author(authorDto.getName(), authorDto.getSurname());
    }

    public List<AuthorDto> getAllAuthors(){ ... }
    public AuthorDto getAuthorById(Long id){ ... }
    public AuthorDto createAuthor(AuthorDto authorDto){ ... }
    public AuthorDto updateAuthor(Long id, AuthorDto dto){ ... }
    public void deleteAuthor(Long id){ ... }
}
```

### Author.java (Entity)

**Пояснение:** класс-сущность JPA, который описывает таблицу авторов в базе данных. Содержит поля id, имя, фамилия и связь один-ко-многим с книгами.

```java
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @OneToMany(mappedBy = "author")
    public List<Book> books = new ArrayList<>();

    public Author() {}
    public Author(String name, String surname) { ... }
}
```

### AuthorDto.java

**Пояснение:** DTO-класс, предназначенный для передачи данных об авторе через API. Содержит те же данные, что и entity, но используется только на уровне контроллера и сервиса, чтобы избежать прямой работы с entity.

```java
public class AuthorDto {
    private Long id;
    private String name;
    private String surname;
    private List<Long> booksIds = new ArrayList<>();

    public AuthorDto() {}
    public AuthorDto(Long id, String name, String surname, List<Long> ids) { ... }
}
```

### AuthorRepository.java

**Пояснение:** интерфейс репозитория, который расширяет JpaRepository. Предоставляет готовые CRUD-методы для работы с базой данных без написания SQL-запросов.

```java
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {}
```

## Заключение

В лабораторной работе реализовано приложение с CRUD-операциями и связями между сущностями. Работа выполнена с использованием Spring Boot, JPA, DTO и протестирована через Swagger.

