package com.lloyds.lazytester.validator;

import com.google.common.collect.ImmutableMap;
import com.lloyds.lazytester.model.Assertions;
import io.vavr.Function3;
import io.vavr.Value;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;

public class Validator {

    private Assertions assertions;
    private Map<String, BodyValidator> bodyValidators = ImmutableMap.<String, BodyValidator>builder()
            .put("contains", new ContainsValidator())
            .put("xpath", new JSonPathValidator())
            .build();

    public Validator(Assertions assertions) {
        this.assertions = assertions;
    }

    public ValidatedAssertions validate(HttpResponse response){
        Validation.Builder3<Seq<String>, Optional<Integer>, Seq<Boolean>, Seq<Boolean>> combine =
                validateStatus(response)
                .combine(validateHeaders(response))
                .combine(validateBody(response));

        Function3<Optional<Integer>, Seq<Boolean>, Seq<Boolean>, Object> f = (p, q, r) -> assertions;
        Validation<List<String>, Object> validation = flatAssertionsError(combine.ap(f));
        return new ValidatedAssertions(validation);
    }

    private Validation<List<String>, Object> flatAssertionsError(Validation<Seq<Seq<String>>, Object> ap) {
        return ap.mapError(seq -> seq.toStream()
                    .flatMap(Value::toStream)
                    .collect(List.collector()));
    }

    private Validation<Seq<String>, Seq<Boolean>> validateHeaders(HttpResponse response) {
        Header[] allHeaders = response.getAllHeaders();

        Function<Header[], Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> mapper = getHeaderValidator();

        List<Validation<Seq<String>, Boolean>> collect = assertions
                .getHeaders()
                .entrySet()
                .stream()
                .map(mapper.apply(allHeaders))
                .collect(List.collector());

        return Validation.sequence(collect);
    }

    private Validation<Seq<String>, Seq<Boolean>> validateBody(HttpResponse response) {
        Map<String, String> bodyAssertions = assertions.getBody();
        if(bodyAssertions.size() == 0){
            return Validation.valid(List.of(Boolean.TRUE));
        }

        String body;
        try {
            body = getBodyAsString(response);
        } catch (Exception e) {
            return Validation.invalid(List.of("Error: "+ e.getMessage()));
        }

        List<Validation<Seq<String>, Boolean>> collect = bodyAssertions
                .entrySet()
                .stream()
                .map(e -> {
                    BodyValidator mapper = getBodyValidator(e.getKey());
                    return mapper.apply(body).apply(e);
                })
                .collect(List.collector());
        return Validation.sequence(collect);
    }

    private String getBodyAsString(HttpResponse response) throws IOException {
        String body;
        HttpEntity entity = response.getEntity();

        InputStream in = entity.getContent();
        body = IOUtils.toString(in);
        return body;
    }

    private Function<Header[], Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> getHeaderValidator() {
        return (headers -> entry -> {
            String jsonPath = entry.getValue();
            for (Header header: headers) {
                if(header.getName().equals(entry.getKey())){
                    return header.getValue().contains(jsonPath)?
                            Validation.valid(Boolean.TRUE)
                            : Validation.invalid(List.of(format("Invalid header assertion [%s] on header[%s]", jsonPath, header)));
                }
            }
            return Validation.invalid(List.of(format("No header key[%s] found in headers[%s]",
                    entry.getKey(),
                    Arrays.toString(headers))));
        });
    }

    private BodyValidator getBodyValidator(String key) {
        return bodyValidators.getOrDefault(key, new ContainsValidator());
    }

    private Validation<Seq<String>, Optional<Integer>> validateStatus(HttpResponse response) {
        return assertions.getOptionalStatus()
                .map(expectedStatus -> {
                    Validation<Seq<String>, Optional<Integer>> res;
                    int actualStatus = response.getStatusLine().getStatusCode();
                    if (expectedStatus.equals(actualStatus)) {
                        res = Validation.valid(Optional.of(expectedStatus));
                    } else {
                        res = Validation.invalid(
                                List.of(
                                        format("Invalid Status: expected[%s] actual[%s]", expectedStatus, actualStatus)));
                    }
                    return res;
                }).orElse(Validation.valid(Optional.empty()));
    }
}
