package com.lloyds.model;

import java.util.LinkedList;
import java.util.List;

public class Scenario {

    public static final Integer DEFAULT_LOOP = 1;

    private String name;
    private Integer loop;
    private Boolean ignoreStepFailures;
    private List<Step> steps;

    private Scenario(String name,
                    Integer loop,
                    Boolean ignoreStepFailures,
                    List<Step> steps) {
        this.name = name;
        this.loop = loop;
        this.ignoreStepFailures = ignoreStepFailures;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public Integer getLoop() {
        return loop;
    }

    public Boolean getIgnoreStepFailures() {
        return ignoreStepFailures;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public static ScenarioBuilder getScenarioBuilder(){
        return new ScenarioBuilder();
    }

    public static class ScenarioBuilder {

        private String name;
        private Integer loop;
        private Boolean ignoreStepFailures;
        private List<Step> steps;

        private ScenarioBuilder() {
            this.name = null;
            this.loop = DEFAULT_LOOP;
            this.ignoreStepFailures = false;
            this.steps = new LinkedList<>();
        }

        public Scenario build() {
            return new Scenario(name, loop, ignoreStepFailures, steps);
        }

        public ScenarioBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ScenarioBuilder setLoop(Integer loop) {
            this.loop = loop;
            return this;
        }

        public ScenarioBuilder setIgnoreStepFailures(Boolean ignoreStepFailures) {
            this.ignoreStepFailures = ignoreStepFailures;
            return this;
        }

        public ScenarioBuilder setSteps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

    }
}
