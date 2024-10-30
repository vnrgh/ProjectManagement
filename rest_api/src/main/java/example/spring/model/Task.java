package example.spring.model;

import example.spring.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tasks", schema = "management")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;
    @Column(name = "task_description")
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String deadline;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
