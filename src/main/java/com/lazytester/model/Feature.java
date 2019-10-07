package com.lazytester.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Feature {

    private List<StepWrapper> steps = new LinkedList<>();
    private List<ScenarioWrapper> scenarios = new LinkedList<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Step> map = null;

    private Map<String, Step> getStepsMap(){
        if(map == null){
            map = steps.stream()
                    .map(StepWrapper::getStep)
                    .collect(Collectors.toMap(Step::getName, Function.identity()));
        }
        return map;
    }

    public List<Step> getSteps(Scenario scenario) {
        return scenario.getSteps().stream()
                .map(getStepsMap()::get)
                .collect(Collectors.toList());
    }
}
