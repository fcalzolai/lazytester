package com.lloyds.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Assertions {

    private Step step;
    private Optional<Integer> status;
    private Map<String, String> headers;
    private Map<String, String> body;

    private Assertions(Optional<Integer> status, Map<String, String> headers, Map<String, String> body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public Optional<Integer> getStatus() {
        // Use local value, otherwise parent, otherwise empty
        return Optional.ofNullable(
                status.orElseGet(() -> step.getParent()
                        .map(Step::getAssertions)
                        .flatMap(Assertions::getStatus)
                        .orElse( null)));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public static AssertionBuilder getBuilder(){
        return new AssertionBuilder();
    }


    public static class AssertionBuilder {

        private Optional<Integer> status;
        private Map<String, String> header;
        private Map<String, String> body;

        public AssertionBuilder() {
            status = Optional.empty();
            header = new HashMap<>();
            body = new HashMap<>();
        }

        public Assertions build(){
            return new Assertions(status, header, body);
        }

        public AssertionBuilder setStatus(Integer status) {
            this.status = Optional.ofNullable(status);
            return this;
        }

        public AssertionBuilder setHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public String putBody(String key, String value) {
            return body.put(key, value);
        }

        public String putHeader(String key, String value) {
            return header.put(key, value);
        }
    }
}
