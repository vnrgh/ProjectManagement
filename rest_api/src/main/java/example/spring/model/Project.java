package example.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "projects", schema = "management")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "project_description")
    private String projectDescription;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "project_technology",
    joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "project_id")} ,
    inverseJoinColumns = {@JoinColumn(name = "technology_id", referencedColumnName = "technology_id")})
    private List<Technology> technologies;
}
