package example.spring.service;

import example.spring.TaskNotFoundException;
import example.spring.model.Project;
import example.spring.model.dto.ProjectDTO;
import example.spring.repository.ProjectRepository;
import org.springframework.stereotype.Service;

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
        //todo project not found exception
        return projectRepository.getProjectById(id).orElseThrow(null);
    }
}
