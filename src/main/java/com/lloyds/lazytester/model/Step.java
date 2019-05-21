package com.lloyds.lazytester.model;

import lombok.Data;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Step {

    private String name;
    private Integer loop;
    private String url;
    private Operation operation;
    private String body;
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> headers = new HashMap<>();
    private Assertions assertions;

    public String getFullUrl() {
        StringBuilder fullUrl = new StringBuilder()
                .append(getUrl());

        if(params.size() > 0){
            String mappedParam = params.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            fullUrl.append("?");
            fullUrl.append(mappedParam);
        }

        return fullUrl.toString();
    }

    public HttpUriRequest getHttpRequest() throws UnsupportedEncodingException {
        switch (getOperation().name()) {
            case HttpGet.METHOD_NAME:
                HttpGet httpGet = new HttpGet(getUrl());
                getHeaders().forEach(httpGet::setHeader);
                return httpGet;

            case HttpPost.METHOD_NAME:
                HttpPost httpPost = new HttpPost(getUrl());
                getHeaders().forEach(httpPost::setHeader);

                if (body != null) {
                    httpPost.setEntity(new StringEntity(getBody()));
                }
                return httpPost;

            default:
                throw new IllegalStateException("Method " + getOperation() + " is not supported.");
        }
    }

}
