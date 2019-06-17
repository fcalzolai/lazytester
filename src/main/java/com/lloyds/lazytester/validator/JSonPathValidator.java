package com.lloyds.lazytester.validator;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import net.minidev.json.JSONArray;

import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

public class JSonPathValidator implements BodyValidator {

    //TODO modify the syntax to allow to specify the number of elements expected

    @Override
    public Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>> apply(String body) {
        return (entry -> {
            String jsonPath = entry.getValue();
            DocumentContext jsonContext = JsonPath.parse(body);
            Object read = jsonContext.read(jsonPath);
            boolean result;
            if (read instanceof JSONArray) {
                JSONArray arrays = (JSONArray) read;
                result = !arrays.isEmpty();
            } else {
                result = read != null;
            }
            return result?
                    Validation.valid(Boolean.TRUE)
                    : Validation.invalid(List.of(format("Invalid body assertion [%s] on body[%s]", jsonPath, body)));
        });
    }
}
