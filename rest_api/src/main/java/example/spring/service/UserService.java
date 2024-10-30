package example.spring.service;

import example.logger.aspect.Loggable;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.repository.EmployeeRepository;
import example.spring.repository.RoleRepository;
import example.spring.repository.UserRepository;
import example.spring.security.model.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    public Long createUser(UserDTO userDTO) {
        Role role = roleRepository.findByName("EMPLOYEE");

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

    @Loggable
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EmployeeNotFoundException("User not found"));
        List<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
    }

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());

        if (user.getEmployee() != null) {
            userDTO.setEmployeeId(user.getEmployee().getId());
        }
        return userDTO;
    }
}
