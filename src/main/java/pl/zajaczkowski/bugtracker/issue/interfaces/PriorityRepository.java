package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Priority;
import pl.zajaczkowski.bugtracker.issue.enumes.PriorityName;

import java.util.Optional;

public interface PriorityRepository extends CrudRepository<Priority, Long> {
    Optional<Priority> findByName(PriorityName name);
}
