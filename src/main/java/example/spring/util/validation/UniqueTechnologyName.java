package example.spring.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueTechnologyNameValidation.class)
@Documented
public @interface UniqueTechnologyName {
    String message() default "is not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
