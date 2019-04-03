package com.lloyds.model;

import com.lloyds.runner.ScenarioRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ScenariosResult {

    private Map<Scenario, ScenarioRunner> results;
    private long executionTime;

    public ScenariosResult() {
        results = new HashMap<>();
        executionTime = -1;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public ScenarioRunner get(Scenario scenario) {
        return results.get(scenario);
    }

    public void forEachScenario(BiConsumer<? super Scenario, ? super ScenarioRunner> action){
        results.forEach(action);
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
