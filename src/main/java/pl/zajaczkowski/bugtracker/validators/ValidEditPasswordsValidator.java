package pl.zajaczkowski.bugtracker.validators;

import pl.zajaczkowski.bugtracker.auth.EditPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEditPasswordsValidator implements ConstraintValidator<ValidEditPasswords, EditPassword> {

    @Override
    public boolean isValid(EditPassword editPassword, ConstraintValidatorContext ctx) {
        if (editPassword.getPassword() == null || editPassword.getPassword().equals("")) {
            if (editPassword.getId() == null) {
                ctx.disableDefaultConstraintViolation();
                ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("password")
                        .addConstraintViolation();

                return false;
            }
            return true;
        }
        boolean passwordsAreValid = editPassword.getPassword().equals(editPassword.getRepeatedPassword());
        if (passwordsAreValid) {
            return true;
        } else {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("repeatedPassword")
                    .addConstraintViolation();

            return false;
        }
    }
}
