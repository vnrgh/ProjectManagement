package example.spring.service;

import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.RoleRepository;
import example.spring.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    public Long createUser(UserDTO userDTO) {
        Role role = roleRepository.findByName("CLIENT");

        Employee employee = employeeRepository.findById(userDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + userDTO.getEmployeeId() + " was not found"));

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .roles(List.of(role))
                .employee(employee)
                .build();
        return userRepository.save(user).getId();
    }

//    @Transactional
//    public Long createUser(UserDTO userDTO) {
//        // Находим роль "CLIENT"
//        Role role = roleRepository.findByName("CLIENT");
//
//        // Создаем сотрудника, не загружая полностью Employee, только с его ID
//        Employee employee = new Employee();
//        employee.setId(userDTO.getEmployeeId());
////        employeeRepository.getOne(userDTO.getEmployeeId());
//
//        // Создаем пользователя
//        User user = User.builder()
//                .username(userDTO.getUsername())
//                .password(userDTO.getPassword())
//                .email(userDTO.getEmail())
//                .roles(List.of(role))
//                .employee(employee)  // Присваиваем только ссылку на сотрудника по ID
//                .build();
//
//        return userRepository.save(user).getId();
//    }


    public User getUserById(Long id) {
        // TODO UserNotFoundException
        return userRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // TODO может доделать апдейт и делет
}
