package example.spring.repository;

import example.spring.model.Employee;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private final SessionFactory sessionFactory;

    public EmployeeRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Long createEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(employee);
            session.getTransaction().commit();
            return id;
        }
    }

    public Optional<Employee> getEmployeeById(long id) {
        Employee employee;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            employee = session.get(Employee.class, id);
            session.flush();
            session.getTransaction().commit();
        }
        return Optional.ofNullable(employee);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        try (Session session = sessionFactory.openSession()) {
            employees = session.createQuery("from Employee", Employee.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public void updateEmployeeById(Long id, Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee1 = session.get(Employee.class, id);
            session.beginTransaction();

            employee1.setAge(employee.getAge());
            employee1.setSkill(employee.getSkill());
            employee1.setSalary(employee.getSalary());
            employee1.setFirstName(employee.getFirstName());
            employee1.setLastName(employee.getLastName());

            session.update(employee1);
            session.getTransaction().commit();
        }
    }

    public void deleteEmployeeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Employee employee = session.load(Employee.class, id);
            session.delete(employee);
            session.flush();
            session.getTransaction().commit();
        }
    }
}
