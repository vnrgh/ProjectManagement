package example.spring.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/throw-generic")
    public void throwGenericException() {
        throw new RuntimeException("Unexpected error occurred");
    }

    @GetMapping("/throw-employee-not-found")
    public void throwEmployeeNotFoundException() {
        throw new EmployeeNotFoundException("Employee not found");
    }

    @GetMapping("/throw-project-not-found")
    public void throwProjectNotFoundException() {
        throw new ProjectNotFoundException("Project not found");
    }

    @GetMapping("/throw-task-not-found")
    public void throwTaskNotFoundException() {
        throw new TaskNotFoundException("Task not found");
    }

    @GetMapping("/throw-user-not-found")
    public void throwUserNotFoundException() {
        throw new UserNotFoundException("User not found");
    }
}
