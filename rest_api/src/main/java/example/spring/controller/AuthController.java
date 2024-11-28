package example.spring.controller;

import example.spring.model.dto.SignInRequestDTO;
import example.spring.model.dto.SignInResponseDTO;
import example.spring.model.dto.UserDTO;
import example.spring.security.jwt.TokenProvider;
import example.spring.security.model.UserDetailsImpl;
import example.spring.service.AuthService;
import example.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    private ResponseEntity<Long> signup(@Valid @RequestBody UserDTO userDTO) {
        Long id = authService.signup(userDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/signin")
    private ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO requestDTO) {
        return new ResponseEntity<>(authService.signin(requestDTO), HttpStatus.OK);
    }
}
