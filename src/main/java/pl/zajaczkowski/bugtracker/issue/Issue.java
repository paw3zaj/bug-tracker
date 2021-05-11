package pl.zajaczkowski.bugtracker.issue;

import pl.zajaczkowski.bugtracker.issue.enumes.PriorityName;
import pl.zajaczkowski.bugtracker.issue.enumes.StatusName;
import pl.zajaczkowski.bugtracker.issue.enumes.TypeName;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.project.Project;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Issue {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private StatusName statusName;
    @Column(nullable = false)
    private PriorityName priorityName;
    @Column(nullable = false)
    private TypeName typeName;
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
}