package pl.zajaczkowski.bugtracker.issue;

import pl.zajaczkowski.bugtracker.person.Person;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 150)
    private String content;
    @Column(nullable = false)
    private final LocalDate dateCreated = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Person author;
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;
}
