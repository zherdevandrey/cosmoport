package com.space.validators;

import com.space.anotations.ValidateId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class IdValidator implements ConstraintValidator<ValidateId, Long> {

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
       if (id == (long)id && id > 0){
           return true;
       }
       return false;
    }
}
