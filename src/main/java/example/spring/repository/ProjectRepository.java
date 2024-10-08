package example.spring.repository;

import example.spring.model.Project;
import example.spring.model.Technology;
import example.spring.model.dto.ProjectDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
