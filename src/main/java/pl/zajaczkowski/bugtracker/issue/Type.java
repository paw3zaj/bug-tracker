package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zajaczkowski.bugtracker.issue.enumes.TypeName;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TypeName name;

    public Type(TypeName name) {
        this.name = name;
    }
}
