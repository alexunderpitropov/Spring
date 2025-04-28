package lab02.libraryhibernate.dao;

import lab02.libraryhibernate.entities.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Author> getAuthors() {
        String query = "select a from Author a left join fetch a.books";
        try(Session session = sessionFactory.openSession()){
            return session.createQuery(query, Author.class).list();
        }
    }

    public Author getAuthor(Long id) {
        try(Session session = sessionFactory.openSession()){
            String query = "select a from Author a left join fetch a.books where a.id = :id";
            return session.createQuery(query, Author.class).setParameter("id", id).uniqueResult();
        }
    }

    public void createAuthor(Author author)
    {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
        }
    }

    public Author updateAuthor(Author author){

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(author);
            session.getTransaction().commit();
            return author;
        }
    }

    public void deleteAuthorById(Long id){
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Author author = session.get(Author.class, id);
            session.remove(author);
            session.getTransaction().commit();
        }
    }
}
