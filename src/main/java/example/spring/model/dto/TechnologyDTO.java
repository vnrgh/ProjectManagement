package example.spring.model.dto;

import example.spring.util.validation.UniqueTechnologyName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDTO {
    @NotBlank
    @Size(min = 3, message = "size must be at least 3 symbols")
    @UniqueTechnologyName(message = "is not unique")
    private String name;
}
