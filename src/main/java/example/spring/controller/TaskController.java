package example.spring.controller;

import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    private Task getTaskById(@PathVariable long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    private List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateTask(@PathVariable long id, @RequestBody Task task) {
        taskService.updateTask(id, task);
        return new ResponseEntity<>("Task with id " + id + " was successfully updated", HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<String> deleteTask(long id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>("Task was successfully deleted", HttpStatus.OK);
    }
}
