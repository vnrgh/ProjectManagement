package example.spring.service;

import example.spring.model.dto.SignInRequestDTO;
import example.spring.model.dto.SignInResponseDTO;
import example.spring.model.dto.UserRequestDTO;
import example.spring.security.jwt.TokenProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public AuthService(AuthenticationProvider authenticationProvider, UserService userService, TokenProvider tokenProvider) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    public Long signUp(UserRequestDTO userDTO) {
        return userService.createUser(userDTO);
    }

    public SignInResponseDTO signIn(SignInRequestDTO requestDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());
        try {
            Authentication authenticate = authenticationProvider.authenticate(usernamePasswordAuthenticationToken);
            String token = tokenProvider.createToken(authenticate);
            return new SignInResponseDTO(token);
        } catch (AuthenticationException authenticationException) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
