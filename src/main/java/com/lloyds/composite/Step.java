package com.lloyds.composite;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.lang.String.format;

public class Step {

    private static final BiFunction<String, String, Supplier<IllegalStateException>> EXCEPTION_BUILDER = (name, attr) ->
            () -> new IllegalStateException(format("%s Unable to find valid data for the attribute %s", name, attr));

    private String name;
    private Optional<Step> extend;
    private Optional<Integer> loop;
    private Optional<String> operation;
    private Optional<String> url;
    private Optional<String> request;
    private Optional<String> assertions;

    private Step(String name,
                Optional<Step> extend,
                Optional<Integer> loop,
                Optional<String> operation,
                Optional<String> url,
                Optional<String> request,
                Optional<String> assertions) {
        this.name = name;
        this.extend = extend;
        this.loop = loop;
        this.operation = operation;
        this.url = url;
        this.request = request;
        this.assertions = assertions;
    }

    public String getName() {
        return name;
    }

    public Integer getLoop() {
        return this.loop.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "loop")).getLoop());
    }

    public String getOperation() {
        return this.operation.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "operation")).getOperation());
    }

    public String getUrl() {
        return this.url.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "url")).getUrl());
    }

    public String getRequest() {
        return this.request.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "request")).getRequest());
    }

    public String getAssertions() {
        return this.assertions.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "assertions")).getAssertions());
    }

    public static StepBuilder getStepBuilder(){
        return new StepBuilder();
    }

    public static class StepBuilder {

        private String name;
        private Optional<Step> extend;
        private Optional<Integer> loop;
        private Optional<String> operation;
        private Optional<String> url;
        private Optional<String> request;
        private Optional<String> assertions;

        public StepBuilder() {
            this.name = null;
            this.extend = Optional.empty();
            this.loop = Optional.empty();
            this.operation = Optional.empty();
            this.url = Optional.empty();
            this.request = Optional.empty();
            this.assertions = Optional.empty();
        }

        public Step build(){
            return new Step(name, extend, loop, operation, url, request, assertions);
        }

        public StepBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StepBuilder setExtend(Step parent) {
            this.extend = Optional.of(parent);
            return this;
        }

        public StepBuilder setLoop(Integer loop) {
            this.loop = Optional.of(loop);
            return this;
        }

        public StepBuilder setOperation(String operation) {
            this.operation = Optional.of(operation);
            return this;
        }

        public StepBuilder setUrl(String url) {
            this.url = Optional.of(url);
            return this;
        }

        public StepBuilder setRequest(String request) {
            this.request = Optional.of(request);
            return this;
        }

        public StepBuilder setAssertions(String assertions) {
            this.assertions = Optional.of(assertions);
            return this;
        }
    }
}
