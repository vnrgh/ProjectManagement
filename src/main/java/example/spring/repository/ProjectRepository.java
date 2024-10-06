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
            session.getTransaction().commit();
        }
        return Optional.of(project);
    }

    public List<Project> getAllProjects() {
        List<Project> projects = null;
        try (Session session = sessionFactory.openSession()) {
            projects = session.createQuery("from Project", Project.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects;
    }

    public void updateProjectById(Long id, Project project) {
        try (Session session = sessionFactory.openSession()) {
            Project project1 = session.get(Project.class, id);
            session.beginTransaction();

            project1.setProjectName(project.getProjectName());
            project1.setTechnologies(project.getTechnologies());
            project1.setProjectDescription(project.getProjectDescription());

            session.getTransaction().commit();
        }
    }

    public void deleteProjectById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Project project = session.load(Project.class, id);
            session.delete(project);
            session.getTransaction().commit();
        }
    }

}
