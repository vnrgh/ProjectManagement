package example.spring.repository;

import example.spring.model.Employee;
import example.spring.model.Technology;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TechnologyRepository {
    private final SessionFactory sessionFactory;

    public TechnologyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createTechnology(Technology technology) {
        try(Session session = sessionFactory.openSession()) {
            return (Long) session.save(technology);
        }
    }

    public Optional<Technology> getTechnologyById(Long id) {
        Technology technology;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            technology = session.get(Technology.class, id);
            session.getTransaction().commit();
        }
        return Optional.ofNullable(technology);
    }

    public List<Technology> getAllTechnologies() {
        List<Technology> technologies = null;
        try (Session session = sessionFactory.openSession()) {
            technologies = session.createQuery("from Technology", Technology.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return technologies;
    }

    public void updateTechnologyById(Long id, Technology technology) {
        try (Session session = sessionFactory.openSession()) {
            Technology technology1 = session.get(Technology.class, id);
            session.beginTransaction();

            technology1.setName(technology.getName());

            session.getTransaction().commit();
        }
    }

    public void deleteTechnologyById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Technology technology = session.load(Technology.class, id);
            session.delete(technology);
            session.getTransaction().commit();
        }
    }
}
