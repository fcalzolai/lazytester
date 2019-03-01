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

    public static final Integer DEFAULT_LOOP = 1;

    private static final BiFunction<String, String, Supplier<IllegalStateException>> EXCEPTION_BUILDER = (name, attr) ->
            () -> new IllegalStateException(format("%s Unable to find valid data for the attribute %s", name, attr));

    private String name;
    private Optional<Step> parent;
    private Optional<Integer> loop;
    private Optional<String> operation;
    private Optional<String> url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private Optional<String> body;
    private Assertions assertions;

    private HttpUriRequest httpRequest;

    private Step(String name,
                Optional<Step> parent,
                Optional<Integer> loop,
                Optional<String> operation,
                Optional<String> url,
                Map<String, String> params,
                Map<String, String> headers,
                Optional<String> body,
                Assertions assertions) {
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

    public Optional<Step> getParent() {
        return parent;
    }

    public Integer getLoop() {
        // Use local value, otherwise parent, otherwise default
        return loop.orElseGet(() -> parent.map(Step::getLoop).orElse(DEFAULT_LOOP));
    }

    public String getOperation() {
        return operation.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "operation")).getOperation());
    }

    public String getUrl() {
        return url.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "url")).getUrl());
    }

    public String getFullUrl() {
        StringBuilder fullUrl = new StringBuilder()
                .append(getUrl());

        Map<String, String> params = getParams();
        if(params.size() > 0){
            String mappedParam = params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            fullUrl.append("?");
            fullUrl.append(mappedParam);

        }

        return fullUrl.toString();
    }

    public Map<String, String>  getParams() {
        HashMap<String, String> result = new HashMap<>();
        parent.ifPresent(s -> result.putAll(s.getParams()));
        result.putAll(params);
        return result;
    }

    public Map<String, String> getHeaders() {
        HashMap<String, String> result = new HashMap<>();
        parent.ifPresent(s -> result.putAll(s.getHeaders()));
        result.putAll(headers);
        return result;
    }

    public String getBody() {
        return body.orElseGet(() -> parent.orElseThrow(EXCEPTION_BUILDER.apply(name, "body")).getBody());
    }

    public Assertions getAssertions() {
        return assertions;
    }

    public HttpUriRequest getHttpRequest() throws UnsupportedEncodingException {
        if(httpRequest == null) {
            switch (getOperation()) {
                case HttpGet.METHOD_NAME:
                    httpRequest = new HttpGet(getUrl());
                    getHeaders().forEach((k, v) -> httpRequest.setHeader(k, v));
                    break;
                case HttpPost.METHOD_NAME:
                    HttpPost httpPost = new HttpPost(getUrl());
                    getHeaders().forEach((k, v) -> httpRequest.setHeader(k, v));
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
        private Map<String, String> headers;
        private Optional<String> body;
        private Assertions assertions;

        private StepBuilder() {
            this.name = null;
            this.parent = Optional.empty();
            this.loop = Optional.empty();
            this.operation = Optional.empty();
            this.params = new HashMap<>();
            this.headers = new HashMap<>();
            this.url = Optional.empty();
            this.body = Optional.empty();
            this.assertions = Assertions.getBuilder().build();
        }

        public Step build(){
            Step step = new Step(name, parent, loop, operation, url, params, headers, body, assertions);
            assertions.setStep(step);
            return step;
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

        public StepBuilder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public StepBuilder setBody(String body) {
            this.body = Optional.of(body);
            return this;
        }

        public StepBuilder setAssertions(Assertions assertions) {
            this.assertions = assertions;
            return this;
        }

        public String putParam(String k, String v) {
            return params.put(k, v);
        }

        public String putHeaders(String k, String v) {
            return headers.put(k, v);
        }
    }
}
