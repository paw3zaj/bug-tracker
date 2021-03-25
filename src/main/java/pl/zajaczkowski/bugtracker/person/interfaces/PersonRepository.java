package pl.zajaczkowski.bugtracker.person.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.person.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
