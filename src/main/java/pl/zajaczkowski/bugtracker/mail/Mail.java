package pl.zajaczkowski.bugtracker.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Mail {

    private String recipient;
    private String subject;
    private String content;

    public Mail(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
