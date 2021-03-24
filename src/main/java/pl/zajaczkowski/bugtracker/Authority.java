package pl.zajaczkowski.bugtracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
