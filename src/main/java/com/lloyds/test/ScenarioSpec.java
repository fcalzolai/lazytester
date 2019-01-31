package com.lloyds.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ScenarioSpec {

    private final Optional<Integer> loop;
    private final Boolean ignoreStepFailures;
    private final String scenarioName;
    private final List<OldStep> steps;

    @JsonCreator
    public ScenarioSpec(
            @JsonProperty("stepLoop") Integer loop,
            @JsonProperty("ignoreStepFailures") Boolean ignoreStepFailures,
            @JsonProperty("scenarioName") String scenarioName,
            @JsonProperty("steps") List<OldStep> steps) {
        this.loop = Optional.ofNullable(loop);
        this.ignoreStepFailures = ignoreStepFailures;
        this.scenarioName = scenarioName;
        this.steps = steps;
    }

    public Optional<Integer> getLoop() {
        return loop;
    }

    public Boolean getIgnoreStepFailures() {
        return ignoreStepFailures;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public List<OldStep> getSteps() {
        return steps == null? (new ArrayList<OldStep>()) : steps;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "loop=" + loop +
                ", ignoreStepFailures=" + ignoreStepFailures +
                ", scenarioName='" + scenarioName + '\'' +
                ", steps=" + steps +
                '}';
    }
}
