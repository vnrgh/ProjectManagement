package example.spring.model.dto;

import example.spring.enums.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private int age;
    private double salary;
    private Skill skill;
    private Long userId;
}
