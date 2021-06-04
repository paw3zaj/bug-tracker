package pl.zajaczkowski.bugtracker.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.zajaczkowski.bugtracker.validators.UniqueLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@UniqueLogin
@Setter
@Getter
public class EditPerson {

    private Long id;

    @Size(min = 4)
    private String login;

    @Size(min = 5)
    private String userRealName;

    @Email
    private String email;

    private String phoneNumber;

    private Set<Authority> authorities;

    private Boolean settings = false;

    public EditPerson(Long id, String login, String userRealName, String email, String phoneNumber, Set<Authority> authorities) {
        this.id = id;
        this.login = login;
        this.userRealName = userRealName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }
}
