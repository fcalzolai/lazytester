package com.lloyds.lazytester.model;

import com.lloyds.lazytester.Utils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Feature {

    @Setter(AccessLevel.NONE)
    private List<String> include = new LinkedList<>();

    private List<StepWrapper> steps = new LinkedList<>();

    private List<ScenarioWrapper> scenarios = new LinkedList<>();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Step> map = null;

    public void setInclude(List<String> include) throws FileNotFoundException {
        this.include = include;
        for (String path: include) {
            Feature feature = Utils.parse(path, Feature.class);
            merge(feature);
        }
    }

    public List<Step> getSteps(Scenario scenario) {
        return scenario.getSteps().stream()
                .map(getStepsMap()::get)
                .collect(Collectors.toList());
    }

    private void merge(Feature feature) {
        steps.addAll(feature.getSteps());
        scenarios.addAll(feature.getScenarios());
    }

    private Map<String, Step> getStepsMap(){
        if(map == null){
            map = steps.stream()
                    .map(StepWrapper::getStep)
                    .collect(Collectors.toMap(Step::getName, Function.identity()));
        }
        return map;
    }
}
