package pl.zajaczkowski.bugtracker.auth;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, length = 10)
    private String login;
    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @ManyToMany
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
}
