package com.lloyds.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Step2 {

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer loop;

    @JsonProperty
    private String url;

    @Override
    public String toString() {
        return "Step2{" +
                "name='" + name + '\'' +
                ", loop=" + loop +
                ", url='" + url + '\'' +
                '}';
    }
}
