package com.lloyds.ymlmodel;

public class Step {

    private String name;
    private Integer loop;
    private String url;

    @Override
    public String toString() {
        return "Step{" +
                "name='" + name + '\'' +
                ", loop=" + loop +
                ", url='" + url + '\'' +
                '}';
    }
}
