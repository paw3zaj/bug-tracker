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

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Secured({"ROLE_MANAGE_USERS", "ROLE_USERS_TAB"})
    String showPersonList(Model model) {
        Iterable<Person> personList = personService.findAllPersons();
        model.addAttribute("persons", personList);
        return "person/persons";
    }

    @GetMapping("/remove")
    @Secured("ROLE_MANAGE_USERS")
    String removeUser(@RequestParam Long id) {
        personService.disabledPerson(id);
        return "redirect:/persons";
    }

    @GetMapping("/add")
    @Secured("ROLE_MANAGE_USERS")
    String showAdd(Model model) {
        model.addAttribute("authorities", personService.findAllAuthorities());
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_USERS")
    public String save(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authorities", personService.findAllAuthorities());
            model.addAttribute("person", person);
            return "person/add";
        }

        personService.savePerson(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_USERS")
    public String showEdit(@RequestParam Long id, Model model) {
        var person = personService.findById(id).orElse(null);
        if(person == null) {
            return "redirect:/persons";
        }

        var editPerson = new EditPerson(person.getId(), person.getLogin(), person.getUserRealName()
                , person.getEmail(), person.getPhoneNumber()
        , person.getAuthorities());

        model.addAttribute("authorities", personService.findAllAuthorities());
        model.addAttribute("editPerson", editPerson);
        return "person/edit";
    }

    @PostMapping("/update")
    public String update(@Valid EditPerson editPerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("authorities", personService.findAllAuthorities());
            model.addAttribute("editPerson", editPerson);
            return "person/edit";
        }

        personService.savePerson(editPerson);
        return "redirect:/persons";
    }

    @GetMapping("/settings")
    String showUserSettings(Principal principal, Model model) {
        String login = principal.getName();
        Person person = personService.findPersonByLogin(login).orElse(null);
        if(person == null) {
            return "redirect:/persons";
        }

        var editPerson = new EditPerson(person.getId(), person.getLogin(), person.getUserRealName()
                , person.getEmail(), person.getPhoneNumber()
                , person.getAuthorities());

        editPerson.setSettings(true);

        model.addAttribute("authorities", null);
        model.addAttribute("editPerson", editPerson);
        return "person/edit";
    }

    @GetMapping("/editPassword")
    public String showEditPass(@RequestParam Long id, Model model) {
        var editPassword = new EditPassword();
        editPassword.setId(id);
        model.addAttribute("editPassword", editPassword);
        return "person/password";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@Valid EditPassword editPassword,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editPassword", editPassword);
            return "person/password";
        }

        personService.updatePassword(editPassword);
        return "redirect:/persons/settings";
    }
}
