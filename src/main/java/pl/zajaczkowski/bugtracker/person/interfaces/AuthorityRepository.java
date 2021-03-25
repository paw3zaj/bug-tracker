package pl.zajaczkowski.bugtracker.person.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.person.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
