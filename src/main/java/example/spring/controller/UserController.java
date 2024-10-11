package example.spring.controller;

import example.spring.model.User;
import example.spring.model.dto.UserDTO;
import example.spring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    private User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    private ResponseEntity<Long> createUser(@RequestBody UserDTO userDTO) {
        Long id = userService.createUser(userDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
