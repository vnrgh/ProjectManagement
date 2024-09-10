package example.spring.repository;

import example.spring.enums.Difficulty;
import example.spring.model.Employee;
import example.spring.model.EmployeeDescription;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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

    private void getTaskById() {
    }
}
