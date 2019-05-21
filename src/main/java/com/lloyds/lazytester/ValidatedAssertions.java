package com.lloyds.lazytester;

import com.lloyds.lazytester.model.Assertions;
import io.vavr.collection.List;
import io.vavr.control.Validation;

import java.util.function.Function;

public class ValidatedAssertions {

    public static final ValidatedAssertions EMPTY = new ValidatedAssertions(Validation.valid(null));

    private Validation<List<String>, Assertions> validation;
    private long executionTime;

    public ValidatedAssertions(Validation<List<String>, Assertions> validation) {
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

    @Override
    public String toString() {
        return "ValidatedAssertions{" +
                "executionTime=" + executionTime +
                ", validation=" + validation +
                '}';
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
