package example.spring.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
@Documented
public @interface UniqueUsername {
    // error message
    String message() default "username is not unique";
    //represents group of constraints
    Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
