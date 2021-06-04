package pl.zajaczkowski.bugtracker.auth;

import lombok.Getter;
import lombok.Setter;
import pl.zajaczkowski.bugtracker.validators.ValidEditPasswords;

import javax.validation.constraints.Size;

@ValidEditPasswords
@Getter
@Setter
public class EditPassword {

    private Long id;

    @Size(min = 8)
    private String password;

    private String repeatedPassword;
}
