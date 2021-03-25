package pl.zajaczkowski.bugtracker.project.interfaces;

import org.springframework.data.repository.CrudRepository;
import pl.zajaczkowski.bugtracker.project.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
