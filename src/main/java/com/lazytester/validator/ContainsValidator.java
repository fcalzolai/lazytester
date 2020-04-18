package com.lazytester.validator;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

public class ContainsValidator implements BodyValidator {

    @Override
    public Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>> apply(String body) {
        return (entry -> {
            String jsonPath = entry.getValue();
            return body.contains(jsonPath)?
                    Validation.valid(Boolean.TRUE)
                    : Validation.invalid(List.of(format("Invalid body assertion [%s] on body[%s]", jsonPath, body)));
        });
    }
}
