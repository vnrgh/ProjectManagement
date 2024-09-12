package example.spring.service;

import example.spring.TaskNotFoundException;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Long createTask(TaskDTO task) {
        return taskRepository.createTask(task);
    }

    public Task getTaskById(long id) {
        return taskRepository.getTaskById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " was not found"));
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void updateTask(long id, Task task) {
        taskRepository.updateTask(id, task);
    }

    public void deleteTask(long id) {
        taskRepository.deleteTask(id);
    }
}
