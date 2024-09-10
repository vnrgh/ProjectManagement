package example.spring.model.dto;

import example.spring.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long projectId;
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String deadline;
}
