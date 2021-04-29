package pl.zajaczkowski.bugtracker.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
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
    private Boolean enabled = true;
    @Column(nullable = false)
    private String userRealName;
    private String email;
    private Integer phoneNumber;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    public Person(String login, String password, String userRealName) {
        this.login = login;
        this.password = password;
        this.userRealName = userRealName;
    }

    void isDisabled() {
        this.enabled = false;
    }
}
