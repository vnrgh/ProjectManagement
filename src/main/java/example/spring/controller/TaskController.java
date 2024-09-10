package example.spring.controller;

import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    private ResponseEntity<Long> createTask(@RequestBody TaskDTO task) {
        Long id = taskService.createTask(task);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
