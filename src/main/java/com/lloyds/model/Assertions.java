package com.lloyds.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Assertions {

    private Step step;
    private Optional<Integer> status;
    private Map<String, String> headers;
    private Optional<String> body;

    private Assertions(Optional<Integer> status, Map<String, String> headers, Optional<String> body) {
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

    public Optional<String> getBody() {
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
        private Optional<String> body;

        public AssertionBuilder() {
            status = Optional.empty();
            header = new HashMap<>();
            body = Optional.empty();
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

        public AssertionBuilder setBody(String body) {
            this.body = Optional.ofNullable(body);
            return this;
        }

        public String put(String key, String value) {
            return header.put(key, value);
        }
    }
}
