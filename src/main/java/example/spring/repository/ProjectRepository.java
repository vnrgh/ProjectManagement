package example.spring.repository;

import example.spring.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {
    private final SessionFactory sessionFactory;

    public ProjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createProject(Project project) {
        try(Session session = sessionFactory.openSession()) {
            return (Long) session.save(project);
        }
    }
}
