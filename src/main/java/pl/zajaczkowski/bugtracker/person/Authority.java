package pl.zajaczkowski.bugtracker.person;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}