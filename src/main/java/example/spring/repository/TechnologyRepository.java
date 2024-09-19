package example.spring.repository;

import example.spring.model.Technology;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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
            technology = session.get(Technology.class, id);
            session.flush();
        }
        return Optional.ofNullable(technology);
    }

}
