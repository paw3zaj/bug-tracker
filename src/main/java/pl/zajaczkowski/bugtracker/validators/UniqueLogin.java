package pl.zajaczkowski.bugtracker.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginUniquenessValidator.class)
public @interface UniqueLogin {
    String message() default "{login.unique.error}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
