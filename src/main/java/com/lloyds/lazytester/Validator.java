package com.lloyds.lazytester;

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
    private HttpResponse response;

    public Validator(Assertions assertions, HttpResponse response) {
        this.assertions = assertions;
        this.response = response;
    }

    public ValidatedAssertions validate(){
        Validation.Builder3<Seq<String>, Optional<Integer>, Seq<Boolean>, Seq<Boolean>> combine =
                validateStatus()
                .combine(validateHeaders())
                .combine(validateBody());

        Function3<Optional<Integer>, Seq<Boolean>, Seq<Boolean>, Assertions> f = (p, q, r) -> assertions;
        Validation<List<String>, Assertions> assertions = flatAssertionsError(combine.ap(f));
        return new ValidatedAssertions(assertions);
    }

    private Validation<List<String>, Assertions> flatAssertionsError(Validation<Seq<Seq<String>>, Assertions> ap) {
        return ap.mapError(seq -> seq.toStream()
                    .flatMap(Value::toStream)
                    .collect(List.collector()));
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
        if(bodyAssertions.size() == 0){
            return Validation.valid(List.of(Boolean.TRUE));
        }

        String body;
        try {
            body = getBodyAsString();
        } catch (Exception e) {
            return Validation.invalid(List.of("Error: "+ e.getMessage()));
        }

        Function<String, Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> mapper = getBodyValidator();

        List<Validation<Seq<String>, Boolean>> collect = bodyAssertions
                .entrySet()
                .stream()
                .map(mapper.apply(body))
                .collect(List.collector());
        return Validation.sequence(collect);
    }

    private String getBodyAsString() throws IOException {
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

    private Function<String, Function<Map.Entry<String, String>, Validation<Seq<String>, Boolean>>> getBodyValidator() {
        return (body -> entry -> {
                String jsonPath = entry.getValue();
                return body.contains(jsonPath)?
                          Validation.valid(Boolean.TRUE)
                        : Validation.invalid(List.of(format("Invalid body assertion [%s] on body[%s]", jsonPath, body)));
            });
    }

    private Validation<Seq<String>, Optional<Integer>> validateStatus() {
        return assertions.getOptionalStatus()
                .map((expectedStatus) -> {
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
