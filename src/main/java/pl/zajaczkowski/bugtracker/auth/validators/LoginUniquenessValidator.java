package pl.zajaczkowski.bugtracker.auth.validators;

import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.auth.PersonService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class LoginUniquenessValidator implements ConstraintValidator<UniqueLogin, Person> {

    private final PersonService personService;

    public LoginUniquenessValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext ctx) {

        Optional<Person> optionalPerson = personService.findPersonByLogin(person.getLogin());
        if(optionalPerson.isEmpty()){
            return true;
        }
        boolean loginIsUnique = person.getId() != null && optionalPerson.get().getId().equals(person.getId());

        if (!loginIsUnique) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("login")
                    .addConstraintViolation();
        }

        return loginIsUnique;
    }
}
