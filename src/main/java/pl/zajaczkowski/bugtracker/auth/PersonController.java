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
    String showPersonList(Principal principal, Model model) {
        var login = principal.getName();
        var access = personService.checkAccess(login, AuthorityName.ROLE_MANAGE_USERS);
        var permission = personService.hasPermission(login);
        var personList = personService.findAllPersons();

        model.addAttribute("access", access);
        model.addAttribute("permission", permission);
        model.addAttribute("currentPage", "persons");
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
    String showAdd(Principal principal, Model model) {
        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("authorities", personService.findAllAuthorities());
        model.addAttribute("permission", permission);
        model.addAttribute("currentPage", "persons");
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_USERS")
    public String save(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {

            model.addAttribute("authorities", personService.findAllAuthorities());
            model.addAttribute("permission", true);
            model.addAttribute("currentPage", "persons");
            model.addAttribute("person", person);
            return "person/add";
        }

        personService.savePerson(person);
        return "redirect:/persons";
    }

    @GetMapping("/edit")
    @Secured("ROLE_MANAGE_USERS")
    public String showEdit(@RequestParam Long id,Principal principal, Model model) {
        var person = personService.findById(id).orElse(null);
        if(person == null) {
            return "redirect:/persons";
        }

        var permission = personService.hasPermission(principal.getName());
        var editPerson = new EditPerson(person.getId(), person.getLogin(), person.getUserRealName()
                , person.getEmail(), person.getPhoneNumber()
        , person.getAuthorities());

        model.addAttribute("authorities", personService.findAllAuthorities());
        model.addAttribute("currentPage", "persons");
        model.addAttribute("permission", permission);
        model.addAttribute("editPerson", editPerson);
        return "person/edit";
    }

    @PostMapping("/update")
    public String update(@Valid EditPerson editPerson, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            var permission = personService.hasPermission(principal.getName());

            model.addAttribute("authorities", personService.findAllAuthorities());
            model.addAttribute("currentPage", "persons");
            model.addAttribute("permission", permission);
            model.addAttribute("editPerson", editPerson);
            return "person/edit";
        }

        personService.savePerson(editPerson);

        if(editPerson.getSettings()) {
            return "redirect:/projects";
        }
        return "redirect:/persons";
    }

    @GetMapping("/settings")
    String showUserSettings(Principal principal, Model model) {
        String login = principal.getName();
        Person person = personService.findPersonByLogin(login).orElse(null);
        if(person == null) {
            return "redirect:/projects";
        }

        var permission = personService.hasPermission(login);
        var editPerson = new EditPerson(person.getId(), person.getLogin(), person.getUserRealName()
                , person.getEmail(), person.getPhoneNumber()
                , person.getAuthorities());

        editPerson.setSettings(true);

        model.addAttribute("authorities", null);
        model.addAttribute("currentPage", "persons");
        model.addAttribute("permission", permission);
        model.addAttribute("editPerson", editPerson);
        return "person/edit";
    }

    @GetMapping("/editPassword")
    public String showEditPass(@RequestParam Long id, Principal principal, Model model) {
        var editPassword = new EditPassword();
        editPassword.setId(id);

        var permission = personService.hasPermission(principal.getName());

        model.addAttribute("currentPage", "persons");
        model.addAttribute("permission", permission);
        model.addAttribute("editPassword", editPassword);
        return "person/password";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@Valid EditPassword editPassword,
                                 BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            var permission = personService.hasPermission(principal.getName());

            model.addAttribute("currentPage", "persons");
            model.addAttribute("permission", permission);
            model.addAttribute("editPassword", editPassword);
            return "person/password";
        }

        personService.updatePassword(editPassword);
        return "redirect:/persons/settings";
    }
}
