package example.spring.controller;

import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.model.dto.UserResponseDTO;
import example.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponseDTO userResponseDTO = userService.convertToUserDTO(user);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    private List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> userResponseDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userResponseDTOs, HttpStatus.OK).getBody();
    }
}
