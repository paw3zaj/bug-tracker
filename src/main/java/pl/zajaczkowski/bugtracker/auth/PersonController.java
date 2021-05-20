package pl.zajaczkowski.bugtracker.auth;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personsService;

    public PersonController(PersonService personsService) {
        this.personsService = personsService;
    }

    @GetMapping
    @Secured("ROLE_MANAGE_USERS")
    String showPersonList(Model model) {
        Iterable<Person> personList = personsService.findAllPersons();
        model.addAttribute("persons", personList);
        return "person/persons";
    }

    @GetMapping("/remove")
    @Secured("ROLE_MANAGE_USERS")
    String removeUser(@RequestParam Long id) {
        personsService.disabledPerson(id);
        return "redirect:/persons";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_USERS")
    String showAdd(Model model) {
        model.addAttribute("authorities", personsService.findAllAuthorities());
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_USERS")
    public String showUpdate(@RequestParam Long id, Model model) {
        Person person = personsService.findById(id).orElse(null);
        if(person == null) {
            return "redirect:/persons";
        }

        model.addAttribute("authorities", personsService.findAllAuthorities());
        model.addAttribute("person", person);
        return "person/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_USERS")
    public String save(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authorities", personsService.findAllAuthorities());
            model.addAttribute("person", person);
            return "person/add";
        }

        personsService.savePerson(person);
        return "redirect:/persons";
    }

    @GetMapping("/settings")
    @Secured("ROLE_MANAGE_USERS")
    String showUserSettings(Principal principal, Model model) {
        String login = principal.getName();
        Person person = personsService.findPersonByLogin(login).orElseThrow();
        person.setSettings(true);

        model.addAttribute("authorities", personsService.findAllAuthorities());
        model.addAttribute("person", person);
        return "person/add";
    }
}
