package pl.zajaczkowski.bugtracker.issue.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.issue.Status;
import pl.zajaczkowski.bugtracker.issue.enumes.StatusName;

import java.util.Optional;

public interface StatusRepository extends CrudRepository<Status, Long> {
    Optional<Status> findByName(StatusName name);
}
