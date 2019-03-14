package com.lloyds.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Assertions {

    private Step step;
    private Optional<Integer> status;
    private Map<String, String> headers;
    private Map<String, String> body;

    private Map<String, String> bodyToReturn;
    private Map<String, String> headersToReturn;

    private Assertions(Step step, Optional<Integer> status, Map<String, String> headers, Map<String, String> body) {
        this.step = step;
        this.status = status;
        this.headers = headers;
        this.body = body;
        this.bodyToReturn = null;
        this.headersToReturn = null;
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
        if(headersToReturn == null) {
            headersToReturn = new HashMap<>();
            step.getParent().ifPresent(s -> headersToReturn.putAll(s.getAssertions().getHeaders()));
            headersToReturn.putAll(headers);
        }
        return headersToReturn;
    }

    public Map<String, String> getBody() {
        if(bodyToReturn == null) {
            bodyToReturn = new HashMap<>();
            step.getParent().ifPresent(s -> bodyToReturn.putAll(s.getAssertions().getBody()));
            bodyToReturn.putAll(body);
        }

        return bodyToReturn;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public static AssertionBuilder getBuilder(){
        return new AssertionBuilder();
    }

    @Override
    public String toString() {
        return "Assertions{" +
                "step=" + step +
                ", status=" + getStatus() +
                ", headers=" + getHeaders() +
                ", body=" + getBody() +
                '}';
    }

    public static class AssertionBuilder {

        private Step step;
        private Optional<Integer> status;
        private Map<String, String> header;
        private Map<String, String> body;

        public AssertionBuilder() {
            status = Optional.empty();
            header = new HashMap<>();
            body = new HashMap<>();
        }

        public Assertions build(){
            return new Assertions(step, status, header, body);
        }

        public AssertionBuilder setStatus(Integer status) {
            this.status = Optional.ofNullable(status);
            return this;
        }

        public AssertionBuilder setStep(Step step) {
            this.step = step;
            return this;
        }

        public AssertionBuilder putBody(String key, String value) {
            body.put(key, removeDoulbeQuote(value));
            return this;
        }

        public AssertionBuilder putHeader(String key, String value) {
            header.put(key, removeDoulbeQuote(value));
            return this;
        }

        private String removeDoulbeQuote(String str) {
            if(str.startsWith("\"") && str.endsWith("\"")){
                str = str.replaceAll("\"", "");
            }
            return str;
        }
    }
}
