package example.spring.controller.integration;

import example.spring.config.TestContainerConfig;
import example.spring.enums.Difficulty;
import example.spring.enums.Skill;
import example.spring.model.Employee;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerIT {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;
    private Project project;
    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        project = new Project(1L, "name", "description", null);
        employees = List.of(
                new Employee(1L, "John1", "Doe1", 20, 1000.0, Skill.JUNIOR, null));
    }

    @Test
    void createTaskTest() throws Exception {
        TaskDTO taskDTO = new TaskDTO(1L, project.getId(), "description", Difficulty.EASY, "now", List.of(employees.getFirst().getId()));


        mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTaskByIdTest() throws Exception {
        Task task = new Task(1L, "description", Difficulty.EASY, "now", project, employees);
        Task savedTask = taskRepository.save(task);

        mockMvc.perform(get("/api/task/{id}", savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskDescription").value("description"));
    }

    @Test
    void getAllTasksTest() throws Exception {
        List<Task> tasks = List.of(new Task(1L, "description1", Difficulty.EASY, "now", project, employees),
                new Task(2L, "description2", Difficulty.HARD, "now", project, employees));
        taskRepository.saveAll(tasks);

        mockMvc.perform(get("/api/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void updateTaskByIdTest() throws Exception {
        Task task = new Task(1L, "description", Difficulty.EASY, "now", project, employees);
        Task savedTask = taskRepository.save(task);
        TaskDTO taskDTO = new TaskDTO(1L, project.getId(), "description", Difficulty.EASY, "now", List.of(employees.getFirst().getId()));
        String expectedMessage = "Task with id " + savedTask.getId() + " successfully updated";

        mockMvc.perform(put("/api/task/{id}", savedTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    void deleteTaskByIdTest() throws Exception {
        Task task = new Task(1L, "description", Difficulty.EASY, "now", project, employees);
        Task savedTask = taskRepository.save(task);
        String expectedMessage = "Task with id " + savedTask.getId() + " successfully deleted";

        mockMvc.perform(delete("/api/task/{id}", savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}
