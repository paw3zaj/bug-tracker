package pl.zajaczkowski.bugtracker.validators;

import pl.zajaczkowski.bugtracker.auth.EditPerson;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.auth.PersonService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EditLoginUniquenessValidator implements ConstraintValidator<UniqueLogin, EditPerson> {

    private final PersonService personService;

    public EditLoginUniquenessValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean isValid(EditPerson editPerson, ConstraintValidatorContext ctx) {
        Optional<Person> optionalPerson = personService.findPersonByLogin(editPerson.getLogin());
        if(optionalPerson.isEmpty()){
            return true;
        }
        boolean loginIsUnique = editPerson.getId() != null && optionalPerson.get().getId().equals(editPerson.getId());

        if (!loginIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("login")
                    .addConstraintViolation();
        }

        return loginIsUnique;
    }
}
