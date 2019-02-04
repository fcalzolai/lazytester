package com.lloyds.composite;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static java.lang.String.format;

public class Scenario {

    private static final BiFunction<String, String, Supplier<IllegalStateException>> EXCEPTION_BUILDER = (name, attr) ->
            () -> new IllegalStateException(format("%s Unable to find valid data for the attribute %s", name, attr));

    private String name;
    private Optional<Scenario> extend;
    private Optional<Integer> loop;
    private Optional<Boolean> ignoreStepFailures;
    private Optional<List<Step>> steps;

    private Scenario(String name,
                    Optional<Scenario> extend,
                    Optional<Integer> loop,
                    Optional<Boolean> ignoreStepFailures,
                    Optional<List<Step>> steps) {
        this.name = name;
        this.extend = extend;
        this.loop = loop;
        this.ignoreStepFailures = ignoreStepFailures;
        this.steps = steps;
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

    public static ScenarioBuilder getScenarioBuilder(){
        return new ScenarioBuilder();
    }

    public static class ScenarioBuilder {

        private String name;
        private Optional<Scenario> extend;
        private Optional<Integer> loop;
        private Optional<Boolean> ignoreStepFailures;
        private Optional<List<Step>> steps;

        private ScenarioBuilder() {
            this.name = null;
            this.extend = Optional.empty();
            this.loop = Optional.empty();
            this.ignoreStepFailures = Optional.empty();
            this.steps = Optional.empty();
        }

        public Scenario build() {
            return new Scenario(name, extend, loop, ignoreStepFailures, steps);
        }

        public ScenarioBuilder setExtend(Scenario parent) {
            this.extend = Optional.of(parent);
            return this;
        }

        public ScenarioBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ScenarioBuilder setLoop(Integer loop) {
            this.loop = Optional.of(loop);
            return this;
        }

        public ScenarioBuilder setIgnoreStepFailures(Boolean ignoreStepFailures) {
            this.ignoreStepFailures = Optional.of(ignoreStepFailures);
            return this;
        }

        public ScenarioBuilder setSteps(List<Step> steps) {
            this.steps = Optional.of(steps);
            return this;
        }

    }
}
