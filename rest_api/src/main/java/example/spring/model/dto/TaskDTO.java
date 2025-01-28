package example.spring.model.dto;

import example.spring.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long projectId;
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String deadline;
    private List<Long> employeeIds;
}
