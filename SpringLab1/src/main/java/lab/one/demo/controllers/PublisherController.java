package lab.one.demo.controllers;

import lab.one.demo.dtos.PublisherDto;
import lab.one.demo.entities.Publisher;
import lab.one.demo.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService service;

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAll(){

        List<PublisherDto> publishers = service.allPublishers();
        return ResponseEntity.ok(publishers);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherDto publisherDto){
        PublisherDto createdPublisher = service.createPublisher(publisherDto);
        return ResponseEntity.ok(createdPublisher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherBYId(@PathVariable Long id){
        PublisherDto publisher = service.getPublisherById(id);
        return ResponseEntity.ok(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long id, @RequestBody PublisherDto publisherDto){
        PublisherDto updatedPublisher = service.updatePublisher(id, publisherDto);
        return ResponseEntity.ok(updatedPublisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id){
        service.deletePublisher(id);
        return ResponseEntity.noContent().build();
    }

}
