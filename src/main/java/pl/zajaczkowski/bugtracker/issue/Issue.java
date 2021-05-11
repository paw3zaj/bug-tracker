package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import pl.zajaczkowski.bugtracker.issue.enumes.Priority;
import pl.zajaczkowski.bugtracker.issue.enumes.Status;
import pl.zajaczkowski.bugtracker.issue.enumes.Type;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.project.Project;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Issue {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Status status;
    @Column(nullable = false)
    private Priority priority;
    @Column(nullable = false)
    private Type type;
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