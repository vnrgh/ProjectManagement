package example.spring.repository;

import example.spring.model.Project;
import example.spring.model.Technology;
import example.spring.model.dto.ProjectDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {
    private final SessionFactory sessionFactory;

    public ProjectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createProject(ProjectDTO projectDTO) {
        List<Technology> technologies;
        try (Session session = sessionFactory.openSession()) {

            technologies = session.createQuery("from Technology t where t.id in (:technologyIds)", Technology.class)
                    .setParameter("technologyIds", projectDTO.getTechnologyId())
                    .getResultList();

            Project project = Project.builder()
                    .projectName(projectDTO.getProjectName())
                    .projectDescription(projectDTO.getProjectDescription())
                    .technologies(technologies)
                    .build();

            return (Long) session.save(project);
        }
    }

    public Optional<Project> getProjectById(long id) {
        Project project;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            project = session.get(Project.class, id);
            System.out.println(project.getTechnologies());
            session.flush();
            session.getTransaction().commit();
        }
        return Optional.ofNullable(project);
    }

}
