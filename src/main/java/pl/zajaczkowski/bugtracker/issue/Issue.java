package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.Setter;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.project.Project;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Issue {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    private String description;
    @Column(unique = true, length = 20)
    private String code;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @OneToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Person creator;
    @OneToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private Person assignee;
    @Column(nullable = false)
    private final LocalDate dateCreated = LocalDate.now();
    private LocalDate lastUpdate;
    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;
    private Double estimatedTime;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_statuses",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    private Set<Status> statuses;
    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "issue_types",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private Set<Type> types;
}