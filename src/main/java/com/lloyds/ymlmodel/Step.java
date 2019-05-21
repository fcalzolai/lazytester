package com.lloyds.ymlmodel;

import lombok.Data;

import java.util.Map;

@Data
public class Step {

    private String name;
    private Integer loop;
    private String url;
    private Operation operation;
    private Map<String, String> params;
    private Map<String, String> headers;
}
