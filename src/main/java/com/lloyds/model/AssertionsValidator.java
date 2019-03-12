package com.lloyds.model;


import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.http.HttpResponse;

import java.util.Optional;

import static java.lang.String.format;

public class AssertionsValidator {

    private Assertions assertions;
    private HttpResponse response;

    public AssertionsValidator(Assertions assertions, HttpResponse response) {
        this.assertions = assertions;
        this.response = response;
    }

    public Validation<Seq<String>, Integer> validate(){
        return null;
    }

    private Validation<String, Optional<Integer>> validateBody() {
//        assertions.getBody()
//                .map(body -> {
//
//                })

        return null;
    }

    private Validation<String, Optional<Integer>> validateStatus() {
        return assertions.getStatus()
                .map((expectedStatus) -> {
                    Validation<String, Optional<Integer>> res;
                    int actualStatus = response.getStatusLine().getStatusCode();
                    if (expectedStatus.equals(actualStatus)) {
                        res = Validation.valid(Optional.of(expectedStatus));
                    } else {
                        res = Validation.invalid(format("Invalid Status: expected[%s] actual[%s]", expectedStatus, actualStatus));
                    }
                    return res;
                }).orElse(Validation.valid(Optional.empty()));
    }
}
