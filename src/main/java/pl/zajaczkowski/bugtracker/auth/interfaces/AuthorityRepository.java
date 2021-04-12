package pl.zajaczkowski.bugtracker.auth.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.auth.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
