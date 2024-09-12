package example.spring.repository;

import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class TaskRepository {
    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createTask(TaskDTO taskDTO) {
        Project project;
        try (Session session = sessionFactory.openSession()) {
            project = session.get(Project.class, taskDTO.getProjectId());

            Task task = Task.builder()
                    .taskDescription(taskDTO.getTaskDescription())
                    .deadline(taskDTO.getDeadline())
                    .difficulty(taskDTO.getDifficulty())
                    .project(project)
                    .build();

            task.setProject(project);
            return (Long) session.save(task);
        }
    }

    public Optional<Task> getTaskById(long id) {
        Task task;
        try(Session session = sessionFactory.openSession()) {
            task = session.get(Task.class, id);
            session.flush();
        }
        return Optional.ofNullable(task);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = null;
        try(Session session = sessionFactory.openSession()) {
            tasks = session.createQuery("from Task", Task.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void updateTask(long id, Task task) {
        try(Session session = sessionFactory.openSession()) {
            Task task1 = session.get(Task.class, id);
            session.beginTransaction();

            task1.setTaskDescription(task.getTaskDescription());
            //todo не получается сделать апдейт вместе с полем проджект, видимо из-за того, что они связаны как manytoone

            // task1.setProject(task.getProject());
            task1.setDeadline(task.getDeadline());
            task1.setDifficulty(task.getDifficulty());

            session.update(task1);
            session.getTransaction().commit();
        }
    }

    public void deleteTask(long id) {
        try(Session session = sessionFactory.openSession()) {
            Task task = session.load(Task.class, id);
            session.delete(task);
            session.flush();
        }
    }
}
