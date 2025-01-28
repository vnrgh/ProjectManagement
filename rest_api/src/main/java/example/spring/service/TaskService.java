package example.spring.service;

import example.spring.exception.ProjectNotFoundException;
import example.spring.exception.TaskNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.ProjectRepository;
import example.spring.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    public Long createTask(TaskDTO taskDTO) {
        System.out.println("TaskDto received");
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + taskDTO.getProjectId() + " was not found"));
        System.out.println("Project received");

        List<Employee> employees = employeeRepository.findAllById(taskDTO.getEmployeeIds());
        System.out.println("Employees received");

        Task task = Task.builder()
                .taskDescription(taskDTO.getTaskDescription())
                .difficulty(taskDTO.getDifficulty())
                .deadline(taskDTO.getDeadline())
                .employees(employees)
                .project(project).build();
        System.out.println("Task was built");

        task = taskRepository.save(task);


        project.setTasks(List.of(task));
        System.out.println("List of tasks was set into project");

//        projectRepository.save(project);
//        System.out.println("Project saved");

//        return taskRepository.save(task).getId();
        return task.getId();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " was not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public void updateTask(Long id, TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new TaskNotFoundException("Project with id " + taskDTO.getProjectId() + " was not found"));

//        List<Employee> employees = employeeRepository.findAllById(taskDTO.getEmployeeIds());
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " was not found"));
        existingTask.setTaskDescription(taskDTO.getTaskDescription());
        existingTask.setDifficulty(taskDTO.getDifficulty());
        existingTask.setDeadline(taskDTO.getDeadline());
//        existingTask.setEmployees(employees);
        existingTask.setProject(project);

        taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
