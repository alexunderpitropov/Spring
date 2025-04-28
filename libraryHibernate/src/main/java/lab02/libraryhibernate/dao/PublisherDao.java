package lab02.libraryhibernate.dao;

import lab02.libraryhibernate.entities.Publisher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PublisherDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Publisher> getAllPublishers(){
        try(Session session = sessionFactory.openSession()) {
            String query = "select p from Publisher p left join fetch p.books";
            return session.createQuery(query, Publisher.class).list();
        }
    }

    public Publisher getPublisher(Long id){
        try(Session session = sessionFactory.openSession()) {
            String query = "select p from Publisher p left join fetch p.books where p.id = :id";
            return session.createQuery(query, Publisher.class).setParameter("id", id).uniqueResult();
        }
    }

    public void createPublisher(Publisher publisher){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(publisher);
            session.getTransaction().commit();
        }
    }

    public Publisher updatePublisher(Publisher publisher){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(publisher);
            session.getTransaction().commit();
            return publisher;
        }
    }

    public void deletePublisher(Long id){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Publisher publisher = session.get(Publisher.class, id);
            session.delete(publisher);
            session.getTransaction().commit();
        }
    }
}

