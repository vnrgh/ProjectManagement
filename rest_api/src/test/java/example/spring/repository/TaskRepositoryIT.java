package example.spring.repository;

import example.spring.config.TestContainerConfig;
import example.spring.enums.Difficulty;
import example.spring.enums.Skill;
import example.spring.model.Employee;
import example.spring.model.Project;
import example.spring.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.Assert.assertTrue;

@DataJpaTest
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
class TaskRepositoryIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void existsByEmployeesIdTest() {
        taskRepository.deleteAll();
        employeeRepository.deleteAll();

        List<Employee> employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, null));
        employees = employeeRepository.saveAll(employees);

        Project project = new Project(1L, "project", "test project", null);

        Task task = new Task(1L, "description", Difficulty.EASY, "now", project, employees);
        taskRepository.save(task);

        project.setTasks(List.of(task));
        projectRepository.save(project);

        boolean existsByFirstId = taskRepository.existsByEmployeesId(employees.get(0).getId());

        assertTrue(existsByFirstId);
    }
}
