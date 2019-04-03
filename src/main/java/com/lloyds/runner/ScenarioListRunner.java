package com.lloyds.runner;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class ScenarioListRunner {

    //TODO create synchronous and asynchronous Scenario runners
    private List<ScenarioRunner> scenarios;
    private long executionTime;

    public ScenarioListRunner(List<ScenarioRunner> scenarios) {
        this.scenarios = scenarios;
        this.executionTime = -1;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void runScenarios() throws IOException {
        long start = System.currentTimeMillis();
        for (ScenarioRunner scenario : scenarios) {
            scenario.runScenario();
        }
        executionTime = System.currentTimeMillis()-start;
    }

    public void forEachScenario(Consumer<? super ScenarioRunner> action){
        scenarios.forEach(action);
    }

}
