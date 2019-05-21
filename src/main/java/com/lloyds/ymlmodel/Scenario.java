package com.lloyds.ymlmodel;

import lombok.Data;

import java.util.List;

@Data
public class Scenario {

    private String name;
    private Integer loop;
    private List<String> steps;

}
