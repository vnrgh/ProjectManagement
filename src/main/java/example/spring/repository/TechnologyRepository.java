package example.spring.repository;

import example.spring.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    //todo не знаю зачем этот метод, но пока пусть будет/ он нужен для валидации по имени
    boolean existsByName(String name);

}
