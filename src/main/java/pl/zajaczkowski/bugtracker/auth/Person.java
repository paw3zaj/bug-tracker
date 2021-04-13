package pl.zajaczkowski.bugtracker.auth;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, length = 10)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @ColumnDefault(value = "true")
    Boolean enabled;
    @Column(nullable = false)
    private String userRealName;
    @Column(nullable = false, unique = true)
    private String email;
    Integer phoneNumber;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    public Person(String login, String password, String userRealName
            , String email, Integer phoneNumber, Set<Authority> authorities) {
        this.login = login;
        this.password = password;
        this.userRealName = userRealName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }
}
