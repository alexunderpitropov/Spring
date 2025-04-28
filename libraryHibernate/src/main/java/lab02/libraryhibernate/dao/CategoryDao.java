package lab02.libraryhibernate.dao;

import lab02.libraryhibernate.entities.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.CacheRequest;
import java.util.List;

@Repository
public class CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Category> getAllCategories() {
        try(Session session = sessionFactory.openSession()) {
            String query = "select c from Category c left join fetch c.books";
            return session.createQuery(query, Category.class).list();
        }
    }

    public Category getCategory(Long id) {
        try(Session session = sessionFactory.openSession()) {
            String query = "select c from Category c left join fetch c.books where c.id = :id";
            return session.createQuery(query, Category.class).setParameter("id", id).getSingleResultOrNull();
        }
    }

    public void createCategory(Category category) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    public Category updateCategory(Category category) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(category);
            session.getTransaction().commit();
            return category;
        }
    }
    public void deleteCategory(Category category) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
        }
    }
}
