package lab02.libraryhibernate.controllers;

import lab02.libraryhibernate.dao.PublisherDao;
import lab02.libraryhibernate.dtos.AuthorDto;
import lab02.libraryhibernate.dtos.PublisherDto;
import lab02.libraryhibernate.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public List<PublisherDto> getAllPublishers(){
        return publisherService.getAllPublishers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id){
        PublisherDto publisherDto = publisherService.getPublisherById(id);
        if(publisherDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }else{
            return ResponseEntity.ok(publisherDto);
        }
    }

    @PostMapping
    public void addPublisher(@RequestBody PublisherDto publisherDto){
        publisherService.createPublisher(publisherDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePublisher(@PathVariable Long id, @RequestBody PublisherDto publisherDto){
        PublisherDto updatedPublisher = publisherService.getPublisherById(id);
        if(updatedPublisher == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }
        publisherService.updatePublisher(id, publisherDto);
        return ResponseEntity.ok().body("Publisher updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id){
        PublisherDto publisherDto = publisherService.getPublisherById(id);
        if(publisherDto == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }
        publisherService.deletePublisher(id);
        return ResponseEntity.ok().body("Publisher deleted");
    }
}
