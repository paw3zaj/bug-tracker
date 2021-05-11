package pl.zajaczkowski.bugtracker.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pl.zajaczkowski.bugtracker.issue.Issue;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "project")
    private Set<Issue> issues;
    @ColumnDefault(value = "true")
    private Boolean enabled = true;
    private String code;
    private String description;
    @Column(nullable = false)
    private final LocalDate dateCreated = LocalDate.now();

}