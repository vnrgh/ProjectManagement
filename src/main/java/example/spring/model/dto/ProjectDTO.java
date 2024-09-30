package example.spring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private List<Long> technologyId;
    private String projectName;
    private String projectDescription;
}
