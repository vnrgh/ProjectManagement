package example.spring.model.dto;

import lombok.Data;

@Data
public class SignInRequestDTO {
    private String username;
    private String password;
}
