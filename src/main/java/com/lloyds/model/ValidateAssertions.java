package com.lloyds.model;

import io.vavr.collection.List;
import io.vavr.control.Validation;

import java.util.function.Function;

public class ValidateAssertions {

    private Validation<List<String>, Assertions> validation;

    public ValidateAssertions(Validation<List<String>, Assertions> validation) {
        this.validation = validation;
    }

    public Validation<List<String>, Assertions> getValidation() {
        return validation;
    }

    public boolean isValid() {
        return validation.isValid();
    }

    public boolean isInvalid() {
        return validation.isInvalid();
    }

    public <U> U fold(Function<? super List<String>, ? extends U> fInvalid
                       , Function<? super Assertions, ? extends U> fValid ) {
        return validation.fold(fInvalid, fValid);
    }

    public List<String> getError() {
        List<String> error = validation.getError();
        return error != null? error: List.empty();
    }
}
