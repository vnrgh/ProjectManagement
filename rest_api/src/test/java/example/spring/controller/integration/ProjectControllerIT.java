package example.spring.controller.integration;

import example.spring.config.TestContainerConfig;
import example.spring.model.Project;
import example.spring.model.dto.ProjectDTO;
import example.spring.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProjectControllerIT {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProjectTest() throws Exception {
        ProjectDTO projectDTO = new ProjectDTO("ProjectName", "Project description");

        mockMvc.perform(post("/api/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getProjectByIdTest() throws Exception {
        Project project = new Project(1L, "name", "description", null);
        Project savedProject = projectRepository.save(project);

        mockMvc.perform(get("/api/project/{id}", savedProject.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("name"));
    }

    @Test
    void getAllProjectsTest() throws Exception {
        List<Project> projects = List.of(new Project(1L, "name1", "description1", null),
                new Project(2L, "name2", "description2", null));
        projectRepository.saveAll(projects);

        mockMvc.perform(get("/api/project"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void updateProjectByIdTest() throws Exception{
        Project project = new Project(1L, "name", "description", null);
        ProjectDTO projectDTO = new ProjectDTO("Projectname", "Project description");
        Project savedProject = projectRepository.save(project);
        String expectedMessage = "Project with id " + savedProject.getId() + " updated";

        mockMvc.perform(put("/api/project/{id}", savedProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    void deleteProjectByIdTest() throws Exception {
        Project project = new Project(1L, "name", "description", null);
        Project savedProject = projectRepository.save(project);
        String expectedMessage = "Project with id " + savedProject.getId() + " deleted";


        mockMvc.perform(delete("/api/project/{id}", savedProject.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }
}
