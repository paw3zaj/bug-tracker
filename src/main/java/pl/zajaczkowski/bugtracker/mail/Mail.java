package pl.zajaczkowski.bugtracker.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

    private String recipient;
    private String subject;
    private String content;
}
