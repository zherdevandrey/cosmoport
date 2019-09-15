package com.space.anotations;

import com.space.validators.IdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = IdValidator.class)
@Target({ ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateId {
    String message() default "End date must be after begin date "
            + "and both must be in the future, room number must be bigger than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}