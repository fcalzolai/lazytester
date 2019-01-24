package com.lloyds.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Optional;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Step {

    private final Optional<Integer> loop;
    private final String name;
    private final String operation;
    private final String url;
    private JsonNode request;
    private JsonNode assertions;


    public Optional<Integer> getLoop() {
        return loop;
    }

    public String getName() {
        return name;
    }

    public String getOperation() {
        return operation;
    }

    public String getUrl() {
        return url;
    }

    public JsonNode getRequest() {
        return request;
    }

    public JsonNode getAssertions() {
        return assertions;
    }

    @JsonCreator
    public Step(
            @JsonProperty("stepLoop") Integer loop,
            @JsonProperty("name") String name,
            @JsonProperty("operation") String operation,
            @JsonProperty("url") String url,
            @JsonProperty("request") JsonNode request,
            @JsonProperty("assertions") JsonNode assertions) {
        this.loop = Optional.ofNullable(loop);
        this.name = name;
        this.operation = operation;
        this.request = request;
        this.url = url;
        this.assertions = assertions;
    }

    @Override
    public String toString() {
        return "Step{" +
                "stepLoop=" + loop +
                ", name='" + name + '\'' +
                ", operation='" + operation + '\'' +
                ", url='" + url + '\'' +
                ", request=" + request +
                ", assertions=" + assertions +
                '}';
    }
}
