package example.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.spring.enums.Difficulty;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String deadline;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
//    @ToString.Exclude
    private Project project; // связь с Project устанавливается после регистрации

    @JsonIgnore
    @ManyToMany
//    @ToString.Exclude
    @JoinTable(name = "employee_tasks",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<Employee> employees;
}

