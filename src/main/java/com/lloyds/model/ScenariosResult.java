package com.lloyds.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ScenariosResult {

    private Map<Scenario, ScenarioResult> results;
    private long executionTime;

    public ScenariosResult() {
        results = new HashMap<>();
        executionTime = -1;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public ValidatedAssertions put(Scenario scenario, int i, int j, ValidatedAssertions validatedAssertions) {
        ScenarioResult scenarioResult = results.computeIfAbsent(scenario, s -> new ScenarioResult());
        return scenarioResult.put(i, j, validatedAssertions);
    }

    public ScenarioResult get(Scenario scenario) {
        return results.get(scenario);
    }

    public void forEachScenario(BiConsumer<? super Scenario, ? super ScenarioResult> action){
        results.forEach(action);
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
