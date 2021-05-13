package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Type;
import pl.zajaczkowski.bugtracker.issue.enumes.TypeName;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Long> {
    Optional<Type> findByName(TypeName name);
}
