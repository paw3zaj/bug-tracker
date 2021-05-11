package pl.zajaczkowski.bugtracker.auth;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personsService;

    public PersonController(PersonService personsService) {
        this.personsService = personsService;
    }

    @GetMapping
    String showPersonList(Model model) {
        Iterable<Person> personList = personsService.findAllPersons();
        model.addAttribute("persons", personList);
        return "person/persons";
    }

    @GetMapping("/remove")
    String removeUser(@RequestParam Long id) {
        personsService.disabledPerson(id);
        return "redirect:/persons";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_USERS")
    String showAdd(Model model) {
        Iterable<Authority> authorities = personsService.findAllAuthorities();
        model.addAttribute("authorities", authorities);
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/addUser")
    @Secured("ROLE_MANAGE_USERS")
    public String addUser(@Valid Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/persons/add";
        }
        personsService.savePerson(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit")
    public String showUpdate(@RequestParam Long id, Model model) {
        Optional<Person> optionalPerson = personsService.findById(id);
        optionalPerson.ifPresentOrElse(
                person -> model.addAttribute("person", person),
                () -> new IllegalArgumentException("Invalid user Id:" + id));
        return "person/update";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id, Person person,
                             BindingResult result) {
        if (result.hasErrors()) {
            person.setId(id);
            return "person/update";
        }
        personsService.savePerson(person);
        return "redirect:/persons";
    }

    @GetMapping("/settings")
    String showUserSettings(Principal principal, Model model) {
        String login = principal.getName();
        Optional<Person> optionalPerson = personsService.findPersonByLogin(login);
        optionalPerson.ifPresentOrElse(
                person -> model.addAttribute("person", person),
                () -> new IllegalArgumentException("Invalid login")
        );
        return "user-settings";
    }

    @PostMapping("/change-settings")
    public String changeUserSettings(Person person, BindingResult result) {
        if (result.hasErrors()) {
            return "user-settings";
        }
        personsService.savePerson(person);
        return "redirect:/persons";
    }
}
