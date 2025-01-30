package example.spring.controller;

import example.spring.model.Project;
import example.spring.model.dto.ProjectDTO;
import example.spring.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    private ResponseEntity<Long> createProject(@RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(projectService.createProject(projectDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateProjectById(@PathVariable Long id, @RequestBody Project project) {
        projectService.updateProjectById(id, project);
        return new ResponseEntity<>("Project with id " + id + " updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteProjectById(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>("Project with id " + id + " deleted", HttpStatus.OK);
    }
}
