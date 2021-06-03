package pl.zajaczkowski.bugtracker.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
public class EditPerson {

    private Long id;

    @NotEmpty
    @Size(min = 4)
    private String login;

    @NotEmpty
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
