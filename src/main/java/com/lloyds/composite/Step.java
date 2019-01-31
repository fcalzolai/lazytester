package com.lloyds.composite;

import com.fasterxml.jackson.databind.JsonNode;

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
    private Optional<JsonNode> request;
    private Optional<JsonNode> assertions;

    public Step(String name) {
        this.name = name;
        this.extend = Optional.empty();
        this.loop = Optional.empty();
        this.operation = Optional.empty();
        this.url = Optional.empty();
        this.request = Optional.empty();
        this.assertions = Optional.empty();
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

    public JsonNode getRequest() {
        return this.request.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "request")).getRequest());
    }

    public JsonNode getAssertions() {
        return this.assertions.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "assertions")).getAssertions());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExtend(Step parent) {
        this.extend = Optional.of(parent);
    }

    public void setLoop(Integer loop) {
        this.loop = Optional.of(loop);
    }

    public void setOperation(String operation) {
        this.operation = Optional.of(operation);
    }

    public void setUrl(String url) {
        this.url = Optional.of(url);
    }

    public void setRequest(JsonNode request) {
        this.request = Optional.of(request);
    }

    public void setAssertions(JsonNode assertions) {
        this.assertions = Optional.of(assertions);
    }
}
