package pl.zajaczkowski.bugtracker.project;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/projects")
public class ProjectRESTController {

    private final ProjectService service;
    private final ProjectModelAssembler assembler;

    public ProjectRESTController(ProjectService service, ProjectModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Project>> showAll() {

        List<Project> projectList = (List<Project>) service.findAllProjects();

        List<EntityModel<Project>> projects = projectList.stream()
                .map(assembler :: toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(projects,
                linkTo(methodOn(ProjectRESTController.class).showAll()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showProject(@PathVariable Long id) {

        var project = service.findProjectById(id);

        if (project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Project> projectModel = assembler.toModel(project.get());

        return ResponseEntity.ok().body(projectModel);
    }

}
