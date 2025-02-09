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
        Project project = Project.builder()
                .projectName(projectDTO.getProjectName())
                .projectDescription(projectDTO.getProjectDescription())
                .build();

        return projectRepository.save(project).getId();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void updateProjectById(Long id, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));

        existingProject.setProjectName(projectDTO.getProjectName());
        existingProject.setProjectDescription(projectDTO.getProjectDescription());
        projectRepository.save(existingProject);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
