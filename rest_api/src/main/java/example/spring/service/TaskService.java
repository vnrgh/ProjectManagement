package example.spring.service;

import example.spring.exception.EmployeeNotFoundException;
import example.spring.exception.ProjectNotFoundException;
import example.spring.exception.TaskNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.ProjectRepository;
import example.spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;


    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    public Long createTask(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + taskDTO.getProjectId() + " not found"));

        List<Employee> employees = employeeRepository.findAllById(taskDTO.getEmployeeIds());
        if (employees.size() != taskDTO.getEmployeeIds().size()) {
            throw new EmployeeNotFoundException("One or more employees not found for the provided ids");
        }

        Task task = Task.builder()
                .taskDescription(taskDTO.getTaskDescription())
                .difficulty(taskDTO.getDifficulty())
                .deadline(taskDTO.getDeadline())
                .employees(employees)
                .project(project).build();

        task = taskRepository.save(task);
        project.setTasks(List.of(task));

        return task.getId();
    }

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id).map(this::convertToTaskDTO)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::convertToTaskDTO).toList();
    }

    public void updateTaskById(Long id, TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + taskDTO.getProjectId() + " not found"));

        List<Employee> employees = employeeRepository.findAllById(taskDTO.getEmployeeIds());
        if (employees.size() != taskDTO.getEmployeeIds().size()) {
            throw new EmployeeNotFoundException("One or more employees not found for the provided ids");
        }

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        existingTask.setTaskDescription(taskDTO.getTaskDescription());
        existingTask.setDifficulty(taskDTO.getDifficulty());
        existingTask.setDeadline(taskDTO.getDeadline());
        existingTask.setEmployees(employees);
        existingTask.setProject(project);

        taskRepository.save(existingTask);
    }

    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));

        if (task.getEmployees().isEmpty()) {
            throw new EmployeeNotFoundException("No employees found for task with id " + id);
        }

        task.getEmployees().clear();
        taskRepository.save(task);

        taskRepository.deleteById(id);
    }

    public TaskDTO convertToTaskDTO(Task task) {
        List<Long> employeeIds = task.getEmployees().stream().map(Employee::getId).toList();

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setProjectId(task.getProject().getId());
        taskDTO.setTaskDescription(task.getTaskDescription());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setDifficulty(task.getDifficulty());
        taskDTO.setEmployeeIds(employeeIds);
        taskDTO.setId(task.getId());

        return taskDTO;
    }
}
