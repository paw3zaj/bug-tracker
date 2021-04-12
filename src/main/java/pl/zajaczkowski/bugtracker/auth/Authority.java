package pl.zajaczkowski.bugtracker.auth;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}