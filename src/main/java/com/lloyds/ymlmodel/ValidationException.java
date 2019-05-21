package com.lloyds.ymlmodel;

import java.io.IOException;

public class ValidationException extends IOException {

    public ValidationException(ValidatedAssertions validated) {
        super("Error during validation: " + validated.getError().toString());
    }
}
