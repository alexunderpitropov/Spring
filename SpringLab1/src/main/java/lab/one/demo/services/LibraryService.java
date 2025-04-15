package lab.one.demo.services;

import lab.one.demo.dtos.LibraryDto;
import lab.one.demo.entities.Book;
import lab.one.demo.entities.Library;
import lab.one.demo.repository.BookRepository;
import lab.one.demo.repository.LibraryRepository;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    private List<Long> getBookIds(Library library){
        return library.getBooks();
    }

    private Library mapToEntity(LibraryDto libraryDto){
        return new Library(libraryDto.getName(), libraryDto.getBooks());
    }

    private LibraryDto mapToDto(Library library){
        return new LibraryDto(library.getId(), library.getName(), library.getBooks());
    }

    public List<LibraryDto> getAllLibraries(){
        List<Library> libraries = libraryRepository.findAll();
        List<LibraryDto> dto = new ArrayList<>();
        for(Library lib : libraries){
            dto.add(mapToDto(lib));
        }
        return dto;
    }

    public LibraryDto createLibrary(LibraryDto dto){
        Library createdLibrary = mapToEntity(dto);
        Library saved = libraryRepository.save(createdLibrary);
        return mapToDto(saved);
    }

    public LibraryDto getLibraryById(Long id){
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find this library"));
        return mapToDto(library);
    }

    public void deleteLibrary(Long id){
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find this library"));
        libraryRepository.delete(library);
    }

    public LibraryDto updateLibrary(Long id, LibraryDto dto){
        Library library = libraryRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        List<Long> books = getBookIds(mapToEntity(dto));

        library.setName(dto.getName());
        if(!books.isEmpty()){
            library.setBooks(books);
        }

        Library updated = libraryRepository.save(library);
        return mapToDto(updated);
    }
}
