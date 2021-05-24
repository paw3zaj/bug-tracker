package pl.zajaczkowski.bugtracker.project;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.project.interfaces.ProjectRepository;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    void saveProject(Project project) {
        projectRepository.save(project);
    }

    void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    Optional<Project> findProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

}
