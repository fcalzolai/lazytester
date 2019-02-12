package com.lloyds.model;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Step {

    private static final BiFunction<String, String, Supplier<IllegalStateException>> EXCEPTION_BUILDER = (name, attr) ->
            () -> new IllegalStateException(format("%s Unable to find valid data for the attribute %s", name, attr));

    private String name;
    private Optional<Step> parent;
    private Optional<Integer> loop;
    private Optional<String> operation;
    private Optional<String> url;
    private Map<String, String> params;
    private Optional<String> headers;
    private Optional<String> body;
    private Optional<String> assertions;

    private HttpUriRequest httpRequest;

    private Step(String name,
                Optional<Step> parent,
                Optional<Integer> loop,
                Optional<String> operation,
                Optional<String> url,
                Map<String, String> params,
                Optional<String> headers,
                Optional<String> body,
                Optional<String> assertions) {
        this.name = name;
        this.parent = parent;
        this.loop = loop;
        this.operation = operation;
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.body = body;
        this.assertions = assertions;
    }

    public String getName() {
        return name;
    }

    public Integer getLoop() {
        return this.loop.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "loop")).getLoop());
    }

    public String getOperation() {
        return this.operation.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "operation")).getOperation());
    }

    public String getUrl() {
        return this.url.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "url")).getUrl());
    }

    public String getFullUrl() {
        StringBuilder fullUrl = new StringBuilder()
                .append(getUrl());

        HashMap<String, String> params = getParams();
        if(params.size() > 0){
            String mappedParam = params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            fullUrl.append("?");
            fullUrl.append(mappedParam);

        }

        return fullUrl.toString();
    }

    public HashMap<String, String>  getParams() {
        HashMap<String, String> result = new HashMap<>();
        parent.ifPresent(s -> result.putAll(s.getParams()));
        result.putAll(params);
        return result;
    }

    public String getHeaders() {
        return this.headers.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "headers")).getHeaders());
    }

    public String getBody() {
        return this.body.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "body")).getBody());
    }

    public String getAssertions() {
        return this.assertions.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "assertions")).getAssertions());
    }

    public HttpUriRequest getHttpRequest() throws UnsupportedEncodingException {
        if(httpRequest == null) {
            switch (getOperation()) {
                case HttpGet.METHOD_NAME:
                    httpRequest = new HttpGet(getUrl());
                    break;
                case HttpPost.METHOD_NAME:
                    HttpPost httpPost = new HttpPost(getUrl());
                    httpPost.setEntity(new StringEntity(getBody()));
                    httpRequest = httpPost;
                    break;
                default:
                    throw new IllegalStateException("Method " + getOperation() + " is not supported.");
            }
        }

        return httpRequest;
    }

    public static StepBuilder getStepBuilder(){
        return new StepBuilder();
    }

    public static class StepBuilder {

        private String name;
        private Optional<Step> parent;
        private Optional<Integer> loop;
        private Optional<String> operation;
        private Optional<String> url;
        private Map<String, String> params;
        private Optional<String> headers;
        private Optional<String> body;
        private Optional<String> assertions;

        private StepBuilder() {
            this.name = null;
            this.parent = Optional.empty();
            this.loop = Optional.empty();
            this.operation = Optional.empty();
            this.params = new HashMap<>();
            this.headers = Optional.empty();
            this.url = Optional.empty();
            this.body = Optional.empty();
            this.assertions = Optional.empty();
        }

        public Step build(){
            return new Step(name, parent, loop, operation, url, params, headers, body, assertions);
        }

        public StepBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StepBuilder setParent(Step parent) {
            this.parent = Optional.of(parent);
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

        public StepBuilder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public StepBuilder setHeaders(String headers) {
            this.headers = Optional.of(headers);
            return this;
        }

        public StepBuilder setBody(String body) {
            this.body = Optional.of(body);
            return this;
        }

        public StepBuilder setAssertions(String assertions) {
            this.assertions = Optional.of(assertions);
            return this;
        }
    }
}
