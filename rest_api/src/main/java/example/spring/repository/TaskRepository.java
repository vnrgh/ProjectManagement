package example.spring.repository;

import example.spring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByEmployeesId(Long employeeId);
}
