package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zajaczkowski.bugtracker.issue.enumes.StatusName;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Status {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private StatusName name;

    public Status(StatusName name) {
        this.name = name;
    }
}
