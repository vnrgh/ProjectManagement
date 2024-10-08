package example.spring.model.dto;

import lombok.*;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String projectName;
    private String projectDescription;
    private List<Long> technologyId;
}
