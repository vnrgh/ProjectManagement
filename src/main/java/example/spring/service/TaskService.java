package example.spring.service;

import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Long createTask(TaskDTO task) {
        return taskRepository.createTask(task);
    }
}
