package pl.zajaczkowski.bugtracker.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    String removeUser(@RequestParam Long id){
        personsService.disabledPerson(id);
        return "redirect:/persons";
    }

    @GetMapping("/add")
    String addUser(Model model) {
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/addUser")
    public String addUser(Person person, BindingResult result) {
        if (!result.hasErrors()) {
            personsService.savePerson(person);
        }
        return "redirect:/persons";
    }
}
