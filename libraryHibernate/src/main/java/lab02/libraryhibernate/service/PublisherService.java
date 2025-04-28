package lab02.libraryhibernate.service;

import lab02.libraryhibernate.dao.PublisherDao;
import lab02.libraryhibernate.dtos.AuthorDto;
import lab02.libraryhibernate.dtos.PublisherDto;
import lab02.libraryhibernate.entities.Author;
import lab02.libraryhibernate.entities.Publisher;
import lab02.libraryhibernate.mappers.PublisherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    private PublisherDao publisherDao;

    @Autowired
    private PublisherMapper publisherMapper;

    public List<PublisherDto> getAllPublishers(){
        List<Publisher> publishers = publisherDao.getAllPublishers();
        List<PublisherDto> publisherDtos = new ArrayList<PublisherDto>();
        for(Publisher publisher : publishers){
            publisherDtos.add(publisherMapper.mapToDto(publisher));
        }
        return publisherDtos;
    }

    public PublisherDto getPublisherById(Long id){
        Publisher publisher = publisherDao.getPublisher(id);
        if(publisher == null){
            return null;
        }
        return publisherMapper.mapToDto(publisher);
    }

    public void createPublisher(PublisherDto publisherDto){
        Publisher publisher = publisherMapper.mapToEntity(publisherDto);
        publisherDao.createPublisher(publisher);
    }

    public void updatePublisher(Long id, PublisherDto publisherDto){
        Publisher publisherToUpdate = publisherDao.getPublisher(id);
        if(publisherToUpdate != null){
            publisherToUpdate.setName(publisherDto.getName());
            if(publisherDto.getBooks() != null){
                publisherToUpdate.setBooks(publisherMapper.mapToEntity(publisherDto).getBooks());
            }
            publisherMapper.mapToDto(publisherDao.updatePublisher(publisherToUpdate));
        }
    }

    public void deletePublisher(Long id){
        publisherDao.deletePublisher(id);
    }
}
