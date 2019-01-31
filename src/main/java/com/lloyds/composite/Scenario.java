package com.lloyds.composite;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.lang.String.format;

public class Scenario {

    private static final BiFunction<String, String, Supplier<IllegalArgumentException>> EXCEPTION_BUILDER = (name, attr) ->
            () -> new IllegalArgumentException(format("%s Unable to find valid data for the attribute %s", name, attr));

    private String name;
    private Optional<Scenario> extend;
    private Optional<Integer> loop;
    private Optional<Boolean> ignoreStepFailures;
    private Optional<List<Step>> steps;

    public Scenario(String scenarioName) {
        this.name = scenarioName;
        this.extend = Optional.empty();
        this.loop = Optional.empty();
        this.ignoreStepFailures = Optional.empty();
        this.steps = Optional.empty();
    }

    public String getName() {
        return name;
    }

    public Integer getLoop() {
        return this.loop.orElseGet(
                () -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "loop")).getLoop()
        );
    }

    public Boolean getIgnoreStepFailures() {
        return this.ignoreStepFailures.orElseGet(
                () -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "ignoreStepFailures")).getIgnoreStepFailures()
        );
    }

    public List<Step> getSteps() {
        return this.steps.orElseGet(
                () -> extend.orElseThrow(EXCEPTION_BUILDER.apply(name, "steps")).getSteps()
        );
    }

    public void setExtend(Scenario parent) {
        this.extend = Optional.of(parent);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoop(Integer loop) {
        this.loop = Optional.of(loop);
    }

    public void setIgnoreStepFailures(Boolean ignoreStepFailures) {
        this.ignoreStepFailures = Optional.of(ignoreStepFailures);
    }

    public void setSteps(List<Step> steps) {
        this.steps = Optional.of(steps);
    }
}
