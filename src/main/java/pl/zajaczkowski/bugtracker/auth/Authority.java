package pl.zajaczkowski.bugtracker.auth;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }
}