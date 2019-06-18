package com.lloyds.lazytester.validator;

import io.vavr.collection.List;
import io.vavr.control.Validation;

public class ValidatedAssertions {

    private List<String> validation;
    private long executionTime;

    public ValidatedAssertions(Validation<List<String>, Object> validation) {
        this.validation = validation.isValid() ? List.empty() : validation.getError();
    }

    public boolean isValid() {
        return validation.isEmpty();
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public List<String> getError() {
        return validation;
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

    public long getExecutionTime() {
        return executionTime;
    }
}
