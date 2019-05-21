package com.lloyds.ymlmodel;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Feature {

    private List<ScenarioWrapper> scenarios = new LinkedList<>();
    private List<StepWrapper> steps = new LinkedList<>();

}
