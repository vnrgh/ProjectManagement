package example.spring.service;

import example.spring.exception.ProjectNotFoundException;
import example.spring.model.Project;
import example.spring.model.dto.ProjectDTO;
import example.spring.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProjectServiceUnitTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectRepository projectRepository;

    private Long projectId;
    private ProjectDTO projectDTO;

    @BeforeEach
    void setUp() {
        projectId = 1L;
        projectDTO = new ProjectDTO("project", "test project");
    }

    @Test
    void createProject() {
        Project project = new Project(1L, "project", "test project", null);

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Long id = projectService.createProject(projectDTO);

        assertNotNull(id);
        assertEquals(1L, id);

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void getProjectByIdTest() {
        Project project = new Project(projectId, "project", "test project", null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(projectRepository).findById(projectId);
    }

    @Test
    void getProjectByIdThrowsExceptionTest() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(projectId));

        verify(projectRepository).findById(projectId);
    }

    @Test
    void getAllProjectsTest() {
        List<Project> projects = List.of(
                new Project(1L, "project1", "test project1", null),
                new Project(1L, "project2", "test project2", null));

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("project1", result.get(0).getProjectName());
        assertEquals("project2", result.get(1).getProjectName());

        verify(projectRepository).findAll();
    }

    @Test
    void updateProjectByIdTest() {
        Project project = new Project(projectId, "old Project", "old test project", null);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        projectService.updateProjectById(projectId, projectDTO);

        assertNotNull(project);
        assertEquals(1L, project.getId());
        assertEquals("project", project.getProjectName());

        verify(projectRepository).save(any(Project.class));
    }

    @Test
    void deleteProjectByIdTest() {
        projectService.deleteProjectById(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }
}












