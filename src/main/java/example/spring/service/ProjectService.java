package example.spring.service;

import example.spring.exception.ProjectNotFoundException;
import example.spring.model.Project;
import example.spring.model.dto.ProjectDTO;
import example.spring.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Long createProject(ProjectDTO projectDTO) {
        return (Long) projectRepository.createProject(projectDTO);
    }

    public Project getProjectById(Long id) {
        return projectRepository.getProjectById(id).orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " was not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }

    public void updateProjectById(Long id, Project project) {
        projectRepository.updateProjectById(id, project);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteProjectById(id);
    }
}
