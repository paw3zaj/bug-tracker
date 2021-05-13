package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zajaczkowski.bugtracker.issue.enumes.PriorityName;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Priority {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private PriorityName name;

    public Priority(PriorityName name) {
        this.name = name;
    }
}
