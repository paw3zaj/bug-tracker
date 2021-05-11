package pl.zajaczkowski.bugtracker.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pl.zajaczkowski.bugtracker.auth.validators.UniqueLogin;
import pl.zajaczkowski.bugtracker.auth.validators.ValidPasswords;

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
    @NotEmpty
    @Size(min = 6, message = "musi mieć minimum 6 znaków")
    @Column(nullable = false, unique = true, length = 10)
    private String login;
    @NotEmpty
    @Size(min = 8)
    @Column(nullable = false)
    private String password;
    @Transient
    private String repeatedPassword;
    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean enabled = true;
    @NotEmpty
    @Column(nullable = false)
    private String userRealName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 9, max = 9)
    private String phoneNumber;
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
