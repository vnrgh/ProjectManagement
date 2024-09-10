package example.spring.model;

import example.spring.enums.Skill;
import javax.persistence.*;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "employees", schema = "management")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private double salary;
    @Enumerated(EnumType.STRING)
    private Skill skill;
}