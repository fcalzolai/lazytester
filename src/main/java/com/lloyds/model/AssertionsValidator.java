package com.lloyds.model;

import io.vavr.Function3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.String.format;

public class AssertionsValidator {

    private Assertions assertions;
    private HttpResponse response;

    public AssertionsValidator(Assertions assertions, HttpResponse response) {
        this.assertions = assertions;
        this.response = response;
    }

    public Validation<Seq<Seq<String>>, Assertions> validate(){
        Validation.Builder3<Seq<String>, Optional<Integer>, Seq<Boolean>, Seq<Boolean>> combine =
                validateStatus()
                .combine(validateHeaders())
                .combine(validateBody());

        Function3<Optional<Integer>, Seq<Boolean>, Seq<Boolean>, Assertions> f = (p, q, r) -> assertions;
        return combine.ap(f);
    }

    private Validation<Seq<String>, Seq<Boolean>> validateHeaders() {
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

    private Validation<Seq<String>, Seq<Boolean>> validateBody() {
        Map<String, String> bodyAssertions = assertions.getBody();
        String body;
        try {
            HttpEntity entity1 = response.getEntity();
            if(entity1 == null && bodyAssertions.size() == 0){
                return Validation.valid(io.vavr.collection.List.of(Boolean.TRUE));
            }

            InputStream in = entity1.getContent();
            body = IOUtils.toString(in);
        } catch (Exception e) {
            return Validation.invalid(io.vavr.collection.List.of("Error: "+ e.getMessage()));
        }

        Function<String, Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> mapper = getBodyValidator();

        List<Validation<Seq<String>, Boolean>> collect = bodyAssertions
                .entrySet()
                .stream()
                .map(mapper.apply(body))
                .collect(List.collector());
        return Validation.sequence(collect);
    }

    private Function<Header[], Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> getHeaderValidator() {
        return (headers -> entry -> {
            String jsonPath = entry.getValue();
            for (Header header: headers) {
                if(header.getName().equals(entry.getKey())){
                    return header.getValue().contains(jsonPath)?
                            Validation.valid(Boolean.TRUE)
                            : Validation.invalid(io.vavr.collection.List.of(format("Invalid assertion [%s] on header[%s]", jsonPath, header)));
                }
            }
            return Validation.invalid(io.vavr.collection.List.of(format("No header[%s] found in headers[%s]", entry.getKey(), headers)));
        });
    }

    private Function<String, Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> getBodyValidator() {
        return (body -> entry -> {
                String jsonPath = entry.getValue();
                return body.contains(jsonPath)?
                          Validation.valid(Boolean.TRUE)
                        : Validation.invalid(io.vavr.collection.List.of(format("Invalid assertion [%s] on body[%s]", jsonPath, body)));
            });
    }

    private Validation<Seq<String>, Optional<Integer>> validateStatus() {
        Validation<Seq<String>, Optional<Integer>> optionals = assertions.getStatus()
                .map((expectedStatus) -> {
                    Validation<Seq<String>, Optional<Integer>> res;
                    int actualStatus = response.getStatusLine().getStatusCode();
                    if (expectedStatus.equals(actualStatus)) {
                        res = Validation.valid(Optional.of(expectedStatus));
                    } else {
                        res = Validation.invalid(
                                io.vavr.collection.List.of(
                                        format("Invalid Status: expected[%s] actual[%s]", expectedStatus, actualStatus)));
                    }
                    return res;
                }).orElse(Validation.valid(Optional.empty()));
        return optionals;
    }
}
