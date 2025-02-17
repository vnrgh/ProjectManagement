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

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private double salary;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Skill skill;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.MERGE, orphanRemoval = true)
    private User user;
}
