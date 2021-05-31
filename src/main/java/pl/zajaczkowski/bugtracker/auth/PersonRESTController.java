package pl.zajaczkowski.bugtracker.auth;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Secured("ROLE_MANAGE_USERS")
@RestController
@RequestMapping("/api/persons")
public class PersonRESTController {

    private final PersonService personService;
    private final PersonModelAssembler assembler;

    public PersonRESTController(PersonService personService, PersonModelAssembler assembler) {
        this.personService = personService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Person>> showAll() {
        List<Person> personList = (List<Person>) personService.findAllPersons();

        List<EntityModel<Person>> persons = personList.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(persons,
                linkTo(methodOn(PersonRESTController.class).showAll()).withSelfRel());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showPerson(@PathVariable Long id) {
        var person = personService.findById(id);

        if (person.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Person> personModel = assembler.toModel(person.get());

        return ResponseEntity.ok().body(personModel);
    }
}