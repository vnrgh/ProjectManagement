package example.spring.model.dto;

import example.spring.util.validation.UniqueEmail;
import example.spring.util.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank
    @Size(min = 3, message = "size must be at least 3 symbols")
    @UniqueUsername
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
//    @UniqueEmail
    private String email;
}
