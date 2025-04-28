package lab02.libraryhibernate.config;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory createSessionFactory() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }




}
