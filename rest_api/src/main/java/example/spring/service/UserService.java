package example.spring.service;

import example.logger.aspect.Loggable;
import example.spring.exception.EmployeeNotFoundException;
import example.spring.exception.UserNotFoundException;
import example.spring.model.Employee;
import example.spring.model.Role;
import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.model.dto.UserResponseDTO;
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

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Long createUser(UserDTO userDTO) {
        Role role = roleRepository.findByName("EMPLOYEE");

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .roles(List.of(role))
                .build();

        return userRepository.save(user).getId();
    }


    @Loggable
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    // ---------implements UserDetailsService ----------
    // TODO НА ЧТО ВЛИЯЕТ ЭТОТ МЕТОД И UserDetailsService
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
    }

    public UserResponseDTO convertToUserDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        if (user.getEmployee() != null) {
            userResponseDTO.setEmployeeId(user.getEmployee().getId());
        }

        return userResponseDTO;
    }
}
