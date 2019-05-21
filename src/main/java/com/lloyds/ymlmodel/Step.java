package com.lloyds.ymlmodel;

public class Step {

    private String name;
    private Integer loop;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLoop() {
        return loop;
    }

    public void setLoop(Integer loop) {
        this.loop = loop;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Step{" +
                "name='" + name + '\'' +
                ", loop=" + loop +
                ", url='" + url + '\'' +
                '}';
    }
}
