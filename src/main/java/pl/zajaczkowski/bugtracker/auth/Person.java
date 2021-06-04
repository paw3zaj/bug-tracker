package pl.zajaczkowski.bugtracker.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pl.zajaczkowski.bugtracker.validators.UniqueLogin;
import pl.zajaczkowski.bugtracker.validators.ValidPasswords;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@NoArgsConstructor
@ValidPasswords
@UniqueLogin
@Setter
@Getter
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 4)
    @Column(nullable = false, unique = true, length = 10)
    private String login;

    @JsonIgnore
    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Transient
    private String repeatedPassword;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean enabled = true;

    @Size(min = 5)
    @Column(nullable = false)
    private String userRealName;

    @Email
    private String email;

    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    @Transient
    private Boolean settings = false;

    public Person(String login, String password, String userRealName) {
        this.login = login;
        this.password = password;
        this.userRealName = userRealName;
    }

    void isDisabled() {
        this.enabled = false;
    }

}
