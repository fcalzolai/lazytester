package com.lloyds.runner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import com.lloyds.model.ValidatedAssertions;
import com.lloyds.model.ValidationException;
import com.lloyds.model.Validator;
import io.vavr.control.Validation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScenarioRunner {

    //TODO create synchronous and asynchronous Scenario runners
    private HttpClient httpClient;
    private LinkedList<Scenario> scenarios;

    private Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results;

    public ScenarioRunner(HttpClient httpClient, LinkedList<Scenario> scenarios) {
        this.httpClient = httpClient;
        this.scenarios = scenarios;
        this.results = new HashMap<>();
    }

    public void runScenarios() throws IOException {
        for (Scenario scenario : scenarios) {
            runScenario(scenario);
        }
    }

    public void runScenario(Scenario scenario) throws IOException {
        Table<Integer, Integer, ValidatedAssertions> scenarioResult = getScenarioResult(scenario);
        for (int i = 0; i < scenario.getLoop(); i++) {
            List<Step> steps = scenario.getSteps();
            for (Step step : steps) {
                for (int j = 0; j < step.getLoop(); j++) {
                    ValidatedAssertions validatedAssertions = runStep(step);
                    scenarioResult.put(i, j, validatedAssertions);
                    if (validatedAssertions.isInvalid() && !scenario.getIgnoreStepFailures()) {
                        throw new ValidationException(validatedAssertions);
                    }
                }
            }
        }
    }

    private Table<Integer, Integer, ValidatedAssertions> getScenarioResult(Scenario scenario) {
        return results.computeIfAbsent(scenario, s -> TreeBasedTable.create());
    }

    private ValidatedAssertions runStep(Step step) {
        try {
            HttpUriRequest httpRequest = step.getHttpRequest();
            return getValidatedAssertions(step, httpRequest);
        } catch (Exception e) {
            return new ValidatedAssertions(Validation.invalid(io.vavr.collection.List.of("Exception ignored: "+ e.getMessage())));
        }
    }

    private ValidatedAssertions getValidatedAssertions(Step step, HttpUriRequest httpRequest) throws IOException {
        long start = System.currentTimeMillis();
        HttpResponse response = httpClient.execute(httpRequest);
        long executionTime = System.currentTimeMillis()-start;
        Validator validator = new Validator(step.getAssertions(), response);
        ValidatedAssertions validated = validator.validate();
        validated.setExecutionTime(executionTime);
        return validated;
    }

    public Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> getResults() {
        return ImmutableMap.copyOf(results);
    }
}
