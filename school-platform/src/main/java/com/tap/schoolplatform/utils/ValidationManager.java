package com.tap.schoolplatform.utils;

import jakarta.validation.*;

import java.util.Set;

public class ValidationManager {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validateModel(T model) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<T> v : violations) {
                errorMessages.append(v.getPropertyPath())
                        .append(" - ")
                        .append(v.getMessage())
                        .append("\n");
            }
            throw new ConstraintViolationException("Validation failed:\n" + errorMessages, violations);
        }
    }
}
