package example.spring.service;

import example.spring.model.Project;
import example.spring.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Long createProject(Project project) {
        return (Long) projectRepository.createProject(project);
    }
}
