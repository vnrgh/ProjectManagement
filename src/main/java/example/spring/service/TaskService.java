package example.spring.service;

import example.spring.exception.TaskNotFoundException;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.ProjectRepository;
import example.spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public Long createTask(TaskDTO taskDTO) {
        Project project = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new TaskNotFoundException("Project with id " + taskDTO.getProjectId() + " was not found"));

        Task task = Task.builder()
                .taskDescription(taskDTO.getTaskDescription())
                .difficulty(taskDTO.getDifficulty())
                .deadline(taskDTO.getDeadline())
                .project(project).build();

        return taskRepository.save(task).getId();
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

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " was not found"));
        existingTask.setTaskDescription(taskDTO.getTaskDescription());
        existingTask.setDifficulty(taskDTO.getDifficulty());
        existingTask.setDeadline(taskDTO.getDeadline());
        existingTask.setProject(project);

        taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
