package example.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee_description")
public class EmployeeDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeedescription_id")
    private Long id;
    private String description;

    @OneToOne
    @JoinColumn
    private Employee employee;
}
