package example.spring.service;

import example.spring.enums.Difficulty;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.exception.ProjectNotFoundException;
import example.spring.exception.TaskNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.ProjectRepository;
import example.spring.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    private TaskDTO taskDTO;
    private Project project;
    private Employee employee1;
    private Employee employee2;
    private Long projectId;

    @BeforeEach
    void setUp() {
        projectId = 1L;
        List<Long> employeeIds = List.of(10L, 20L);

        taskDTO = new TaskDTO(1L, projectId, "description", Difficulty.EASY, "tomorrow", employeeIds);

        project = new Project();
        project.setId(projectId);

        employee1 = new Employee();
        employee1.setId(10L);

        employee2 = new Employee();
        employee2.setId(20L);
    }

    @Test
    void createTaskTest() {
        Long taskId = 1L;
        Task task = new Task(taskId, "description", Difficulty.EASY, "tomorrow", project, List.of(employee1, employee2));

        when(employeeRepository.findAllById(taskDTO.getEmployeeIds())).thenReturn(List.of(employee1, employee2));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        Long id = taskService.createTask(taskDTO);

        assertNotNull(id);
        assertEquals(1L, id);

        verify(taskRepository).save(Mockito.any(Task.class));
    }

    @Test
    void createTaskThrowsProjectNotFoundExceptionTest() {
        taskDTO.setProjectId(99L);

        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> taskService.createTask(taskDTO));

        verify(projectRepository, times(1)).findById(99L);
        verify(employeeRepository, Mockito.never()).findAllById(Mockito.any());
        verify(taskRepository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void createTaskThrowsEmployeeNotFoundExceptionTest() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(employeeRepository.findAllById(taskDTO.getEmployeeIds())).thenReturn(List.of(employee1));

        assertThrows(EmployeeNotFoundException.class, () -> taskService.createTask(taskDTO));

        verify(projectRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).findAllById(taskDTO.getEmployeeIds());
        verify(taskRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void getTaskByIdTest() {
        Long taskId = 1L;
        Task task = new Task(taskId, "description", Difficulty.EASY, "tomorrow", project, List.of(employee1, employee2));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskDTO result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(taskRepository).findById(taskId);
    }

    @Test
    void getTaskByIdThrowsExceptionTest() {
        Long taskId = 99L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));

        verify(taskRepository).findById(taskId);
    }

    @Test
    void getAllTasks() {
        List<Task> tasks = List.of(new Task(1L, "description1", Difficulty.EASY, "tomorrow1", project, List.of(employee1, employee2)),
                new Task(2L, "description2", Difficulty.EASY, "tomorrow2", project, List.of(employee1, employee2)));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(taskRepository).findAll();
    }

    @Test
    void updateTask() {
        Long taskId = 1L;
        Task task = new Task(taskId, "blah blah blah", Difficulty.HARD, "now", project, List.of(employee1, employee2));

        when(employeeRepository.findAllById(taskDTO.getEmployeeIds())).thenReturn(List.of(employee1, employee2));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        taskService.updateTaskById(taskId, taskDTO);

        assertEquals(1L, task.getId());
        assertNotNull(task);
        assertEquals("description", task.getTaskDescription());

        verify(taskRepository).save(Mockito.any(Task.class));
    }

    @Test
    void updateTaskThrowsProjectNotFoundException() {
        Long taskId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> taskService.updateTaskById(taskId, taskDTO));

        verify(projectRepository, times(1)).findById(taskDTO.getProjectId());
        verify(employeeRepository, Mockito.never()).findAllById(Mockito.any());
        verify(taskRepository, Mockito.never()).findById(Mockito.any());
        verify(taskRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void updateTaskThrowsEmployeeNotFoundException() {
        Long taskId = 1L;

        when(projectRepository.findById(taskDTO.getProjectId())).thenReturn(Optional.of(project));
        when(employeeRepository.findAllById(taskDTO.getEmployeeIds())).thenReturn(List.of(employee1));

        assertThrows(EmployeeNotFoundException.class, () -> taskService.updateTaskById(taskId, taskDTO));

        verify(projectRepository, times(1)).findById(taskDTO.getProjectId());
        verify(employeeRepository, times(1)).findAllById(taskDTO.getEmployeeIds());
        verify(taskRepository, Mockito.never()).findById(Mockito.any());
        verify(taskRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void updateTaskThrowsTaskNotFoundException() {
        Long taskId = 1L;

        when(projectRepository.findById(taskDTO.getProjectId())).thenReturn(Optional.of(project));
        when(employeeRepository.findAllById(taskDTO.getEmployeeIds())).thenReturn(List.of(employee1, employee2));
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskById(taskId, taskDTO));

        verify(projectRepository, times(1)).findById(taskDTO.getProjectId());
        verify(employeeRepository, times(1)).findAllById(taskDTO.getEmployeeIds());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void deleteTaskById() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setEmployees(new ArrayList<>(List.of(employee1, employee2)));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        taskService.deleteTaskById(taskId);

        assertTrue(task.getEmployees().isEmpty());
        verify(taskRepository).save(task);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTaskByIdThrowsTaskNotFoundException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(99L));
        verify(taskRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteTaskByIdThrowsEmployeeNotFoundException() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setEmployees(Collections.emptyList());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(EmployeeNotFoundException.class, () -> taskService.deleteTaskById(taskId));
        verify(taskRepository, never()).deleteById(anyLong());
    }
}
