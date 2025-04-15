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


    public void deleteAuthor(Long id){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot"));
        authorRepository.delete(author);
    }








}
