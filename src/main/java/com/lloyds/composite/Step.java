package com.lloyds.composite;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
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
    private Optional<String> headers;
    private Optional<String> body;
    private Optional<String> assertions;

    private HttpUriRequest httpRequest;

    private Step(String name,
                Optional<Step> extend,
                Optional<Integer> loop,
                Optional<String> operation,
                Optional<String> url,
                Optional<String> headers,
                Optional<String> body,
                Optional<String> assertions) {
        this.name = name;
        this.extend = extend;
        this.loop = loop;
        this.operation = operation;
        this.url = url;
        this.headers = headers;
        this.body = body;
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

    public String getHeaders() {
        return this.headers.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "headers")).getHeaders());
    }

    public String getBody() {
        return this.body.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "body")).getBody());
    }

    public String getAssertions() {
        return this.assertions.orElseGet(() -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "assertions")).getAssertions());
    }

    public HttpUriRequest createHttpRequest() throws UnsupportedEncodingException {
        if(httpRequest == null) {
            if (getOperation().equals(HttpGet.METHOD_NAME)) {
                httpRequest = new HttpGet(getUrl());
            } else if (getOperation().equals(HttpPost.METHOD_NAME)) {
                HttpPost httpPost = new HttpPost(getUrl());
                httpPost.setEntity(new StringEntity(getBody()));
                httpRequest = httpPost;
            } else {
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
        private Optional<Step> extend;
        private Optional<Integer> loop;
        private Optional<String> operation;
        private Optional<String> url;
        private Optional<String> headers;
        private Optional<String> body;
        private Optional<String> assertions;

        private StepBuilder() {
            this.name = null;
            this.extend = Optional.empty();
            this.loop = Optional.empty();
            this.operation = Optional.empty();
            this.url = Optional.empty();
            this.body = Optional.empty();
            this.assertions = Optional.empty();
        }

        public Step build(){
            return new Step(name, extend, loop, operation, url, headers, body, assertions);
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
