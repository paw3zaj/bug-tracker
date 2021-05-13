package pl.zajaczkowski.bugtracker.auth.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.auth.Authority;
import pl.zajaczkowski.bugtracker.auth.AuthorityName;

import java.util.Optional;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Optional<Authority> findByName(AuthorityName name);
}
