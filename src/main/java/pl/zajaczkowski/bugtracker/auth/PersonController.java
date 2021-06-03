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
    @Secured({"ROLE_MANAGE_USERS", "ROLE_USERS_TAB"})
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

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_USERS")
    public String showEdit(@RequestParam Long id, Model model) {
        var person = personsService.findById(id).orElse(null);
        if(person == null) {
            return "redirect:/persons";
        }

        var editPerson = new EditPerson(person.getId(), person.getLogin(), person.getUserRealName()
                , person.getEmail(), person.getPhoneNumber()
        , person.getAuthorities());

        model.addAttribute("authorities", personsService.findAllAuthorities());
        model.addAttribute("person", editPerson);
        return "person/edit";
    }

    @PostMapping("/update")
    @Secured("ROLE_MANAGE_USERS")
    public String update(@Valid EditPerson editPerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authorities", personsService.findAllAuthorities());
            model.addAttribute("person", editPerson);
            return "person/edit";
        }

        personsService.savePerson(editPerson);
        return "redirect:/persons";
    }

    @GetMapping("/settings")
    String showUserSettings(Principal principal, Model model) {
        String login = principal.getName();
        Person person = personsService.findPersonByLogin(login).orElseThrow();
        person.setSettings(true);

        model.addAttribute("authorities", personsService.findAllAuthorities());
        model.addAttribute("person", person);
        return "person/add";
    }
}
