package example.spring.repository;

import example.spring.enums.Skill;
import example.spring.model.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    
    private final List<Employee> employeeList;

    public EmployeeRepository() {
        this.employeeList = List.of(new Employee(0, "John", "Smith", 30, 3000.00, Skill.MIDDLE),
                                    new Employee(1, "Ivan", "Ivanich", 20, 1500.00, Skill.JUNIOR));
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findAny();
    }

    public Optional<List<Employee>> getAllEmployees() {
        return Optional.of(new ArrayList<>(employeeList));
    }
}
