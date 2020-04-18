package com.lazytester.validator;

import io.vavr.collection.List;
import io.vavr.control.Validation;

public class ValidatedAssertions {

    private List<String> errorList;
    private long executionTime;

    public ValidatedAssertions(Validation<List<String>, Object> errorList) {
        this.errorList = errorList.isValid() ? List.empty() : errorList.getError();
    }

    public boolean isValid() {
        return errorList.isEmpty();
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public List<String> getError() {
        return errorList;
    }

    @Override
    public String toString() {
        return "ValidatedAssertions{" +
                "executionTime=" + executionTime +
                ", isValid=" + isValid() +
                ", errorList=" + errorList +
                '}';
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
