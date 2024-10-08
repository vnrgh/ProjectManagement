package example.spring.repository;

import example.spring.model.Project;
import example.spring.model.Task;
import example.spring.model.dto.TaskDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {

}
