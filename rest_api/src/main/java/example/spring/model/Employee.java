package example.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.spring.enums.Skill;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private int age;
    private double salary;

    @Enumerated(EnumType.STRING)
    private Skill skill;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, optional = true)
    private User user; // Связь с User (устанавливается после регистрации)
}
