
### Тема: Spring Boot приложение для управления библиотекой
#### Условие:
3 контроллера, 3 сервиса и 5 взаимосвязанных сущностей. Приложение должно поддерживать CRUD-операции через REST API и использовать JPA для работы с базой данных.
API должен работать с DTO а не с entity

**Author**: Один автор может написать много книг (One-to-Many).

**Publisher**: Один издатель может издать много книг (One-to-Many).

**Book**:
Каждая книга принадлежит одному автору (Many-to-One).
Каждая книга имеет одного издателя (Many-to-One).
Каждая книга может принадлежать к нескольким категориям (Many-to-Many).

**Category**: Одна категория может быть связана с несколькими книгами (Many-to-Many).


**Library**: Библиотека содержит коллекцию книг в виде списка (ElementCollection).

#### Практическая часть

1. Генерация проекта через spring initiakizer:

![](https://i.imgur.com/0ukZhFc.png)

Включение зависимостей:
**Spring web** - включает модуль для работы с http запросами и возвратом json ответа (использую в контроллерах)

**JPA** - включает Hibernate, ОРМ для работы с бд. ПОзволяет рабоать с бд через репозитории, без анписания SQL скриптов

**postgres driver** - нужен для подключения к posgres бд

**lombok** - для уменьщения шаблонного кода

**docker-support** - пакет, который помог мне размернуть бд в Docker (альтернативные способы я не нашел)

2. Подключение к бд


Благодаря документациям я смог развернуть мою базу данных на базе posgres через Docker.
Создаем конфигурационный файл, в котором будем описывать сервис PosgreSQL:
```
version: '3.8'

volumes:
  pg_demo:

services:
  demo_db:
    image: postgres
    restart: always
    environment:
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=pass
      - POSTGRES_DB=demo_db
    volumes:
      - pg_demo:/var/lib/postgresql/data

    ports:
      - "127.0.0.1:5433:5432"


```

Тут я указал версию docker-compose, создал именованный топ, который будет хранить данные и при перезапуске не удалять данные из бд, определил имя сервиса demo_db, в качестве image использовал официальный образ из docker hub, restart указал для постоянного перезапуска контейнера в случае неполадок

затем были определены переменные окружения, так же указал что бд будет доступна только локально...
Запускаем контейнер, затем благодаря плагину подключась к БД
![](https://i.imgur.com/IYkq0HB.png)

Поскольку я уже закончил работу, таблички в demo_db уже есть, это следующий шаг самой работы - создание сущностей

3. Создание сущностей

Создаем новый пакет отдельно для сущностей entities, в котором буду описывать свои классы и поведение их св-в

Пример(Author)

```java

package lab.one.demo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    public Author() {
    }

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.books = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}


```

**@Entity** - аннотация, указывающая, что это сущность для БД

**@GeneratedValue(strategy = GenerationType.IDENTITY)** - автогенерация Id по мере создания авторов в бд

```java 
@OneToMany(mappedBy = "author")
public List<Book> books = new ArrayList<>();
```
Указываю связь один ко многим, тк 1 автор может быть у нескольких книг, в mappedBy указываю эту связь с классом Book, где поле для автора уже создано

Пример класса `Book`:

```java

package lab.one.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;


    public Book(String title, Author author, Publisher publisher, List<Category> categories) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.categories = categories;
    }

    public Book() {
    }
}


```
```java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "author_id", nullable = false)
private Author author;
```

Несколько книг может принадлежать 1 автору, fetchType - для автозагрузки автора при загрузке книги, JoinColum - создает столбец с id автора, где он явлляется обязательным

Таким образом создаю и другие сущности

4. Создание Dto

Dto - промежуточная сущность, передаваемая между разными слоями приложения. Нужна для сокрытия чувствительных данных
Пример:
```java

package lab.one.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class AuthorDto {
    private Long id;

    private String name;

    private String surname;

    private List<Long> booksIds = new ArrayList<>();

    public AuthorDto() {
    }

    public AuthorDto(Long id, String name, String surname, List<Long> ids) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.booksIds = ids;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}


```

Здесь я ушел от всех навигационных полей, где внещний связи будут теперь передаваться исключительно с id объекта

Таким образом промежуточные сущности были созданы для каждой сущности из бд

5. Создание репозиторий

Репозитории нужны для работы с БД без использования SQL скриптов. Пример:

```java

package lab.one.demo.repository;

import lab.one.demo.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}


```

Аннотацией указываю репозиторий, без нее у меня возникали ошибки при запуске. Создаю именнованный интерфейс, который имплимитирует репозиторий JPA с указанием сущности и типа идентификатора. JPA репозиторий уже содержит в себе все базовые CRUD операции

6. Создание сервисов

Сервис - это слой, который управляет логикой репозитория и его методов

Пример:

```java

package lab.one.demo.services;

import lab.one.demo.dtos.AuthorDto;
import lab.one.demo.entities.Author;
import lab.one.demo.entities.Book;
import lab.one.demo.repository.AuthorRepository;
import lab.one.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    //все авторы
    public List<AuthorDto> getAllAuthors(){
        List<Author> authorList = authorRepository.findAll();
        List<AuthorDto> authorDtoList = new ArrayList<>();

        for(Author author : authorList){
            authorDtoList.add(mapToDto(author));
        }
        return authorDtoList;
    }
    //автор по id
    public AuthorDto getAuthorById(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find this author"));
        return mapToDto(author);
    }
    //создание автора
    public AuthorDto createAuthor(AuthorDto authorDto){
        Author author = mapToEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return mapToDto(savedAuthor);
    }
//obnovochka
    public AuthorDto updateAuthor(Long id, AuthorDto dto){
        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException(" "));
        author.setName(dto.getName());
        author.setSurname(dto.getSurname());
        if(dto.getBooksIds() !=null){
            List<Book> books = bookRepository.findAllById(dto.getBooksIds());
            author.setBooks(books);
        }
        Author updated =  authorRepository.save(author);
        return mapToDto(updated);

    }

//udaliti
    public void deleteAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot"));
        authorRepository.delete(author);
    }

}


```

Здесь для каждого базового CRUD метода я выолняю метод из репозитория, таким образов сервисы ыли созданы для кадой сужности в бд

7. Контроллеры

Контроллеры отвечают за выволнение http запросов, и общается с сервисом

```java

package lab.one.demo.controllers;


import lab.one.demo.dtos.AuthorDto;
import lab.one.demo.services.AuthorService;
import org.apache.coyote.Response;
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
