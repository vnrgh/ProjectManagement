package example.spring.model;

import example.spring.enums.Difficulty;
import lombok.*;

import javax.persistence.*;

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
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    private String deadline;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Project project;
}
