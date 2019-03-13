package com.lloyds.model;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.util.function.Function;

public class ValidateAssertions {

    private Validation<Seq<Seq<String>>, Assertions> validation;

    public ValidateAssertions(Validation<Seq<Seq<String>>, Assertions> validation) {
        this.validation = validation;
    }

    public Validation<Seq<Seq<String>>, Assertions> getValidation() {
        return validation;
    }

    public boolean isValid() {
        return validation.isValid();
    }

    public boolean isInvalid() {
        return validation.isInvalid();
    }

    public <U> U fold(Function<? super Seq<Seq<String>>, ? extends U> fInvalid
                       , Function<? super Assertions, ? extends U> fValid ) {
        return validation.fold(fInvalid, fValid);
    }
}
