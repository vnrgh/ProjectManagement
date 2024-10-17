package example.spring.controller;

import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userService.convertToUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    private List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK).getBody();
    }

    @PostMapping
    private ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        Long id = userService.createUser(userDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
