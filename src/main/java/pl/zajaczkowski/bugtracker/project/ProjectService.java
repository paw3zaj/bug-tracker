package pl.zajaczkowski.bugtracker.project;

import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.project.interfaces.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    Project saveProject(Project project) {
        return projectRepository.save(project);
    }


}
