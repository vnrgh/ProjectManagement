package example.spring.controller;

import example.spring.model.Project;
import example.spring.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    private ResponseEntity<Long> createProject(@RequestBody Project project) {
        Long id = projectService.createProject(project);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
