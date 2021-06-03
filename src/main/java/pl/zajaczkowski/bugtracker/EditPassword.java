package pl.zajaczkowski.bugtracker;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditPassword {

    private Long id;

    @NotEmpty
    @Size(min = 8)
    private String password;

    private String repeatedPassword;
}
