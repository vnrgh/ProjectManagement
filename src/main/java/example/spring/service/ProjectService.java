package example.spring.service;

import example.spring.exception.ProjectNotFoundException;
import example.spring.exception.TechnologyNotFoundException;
import example.spring.model.Project;
import example.spring.model.Technology;
import example.spring.model.dto.ProjectDTO;
import example.spring.model.dto.TechnologyDTO;
import example.spring.repository.ProjectRepository;
import example.spring.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TechnologyRepository technologyRepository;

    public ProjectService(ProjectRepository projectRepository, TechnologyRepository technologyRepository) {
        this.projectRepository = projectRepository;
        this.technologyRepository = technologyRepository;
    }

    public Long createProject(ProjectDTO projectDTO) {
        List<Technology> technologies = projectDTO.getTechnologyId().stream()
                .map(technologyId -> technologyRepository.findById(technologyId)
                        .orElseThrow(() -> new TechnologyNotFoundException("Technology with id " + technologyId + " not found")))
                .toList();
        Project project = Project.builder()
                .projectName(projectDTO.getProjectName())
                .projectDescription(projectDTO.getProjectDescription())
                .technologies(technologies).build();

        return projectRepository.save(project).getId();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " was not found"));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void updateProjectById(Long id, Project project) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " was not found"));

        existingProject.setProjectName(project.getProjectName());
        existingProject.setProjectDescription(project.getProjectDescription());
        existingProject.setTechnologies(project.getTechnologies());
        projectRepository.save(existingProject);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
