package example.spring.controller;

import example.spring.model.dto.SignInRequestDTO;
import example.spring.security.jwt.TokenProvider;
import example.spring.security.model.UserDetailsImpl;
import example.spring.service.UserService;
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
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public AuthController(AuthenticationProvider authenticationProvider, UserService userService, TokenProvider tokenProvider) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    private ResponseEntity signIn(@RequestBody SignInRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());
        try {
            authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            UserDetailsImpl userDetails = userService.loadUserByUsername(requestDTO.getUsername());
            String token = tokenProvider.createToken(userDetails);
            Map<String, String> response = new HashMap<>();
            response.put("auth_token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException authenticationException) {
            throw new BadCredentialsException("Invalid username or password");
        }

    }
}
