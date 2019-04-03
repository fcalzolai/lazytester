package com.lloyds.runner;

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
import java.util.List;
import java.util.function.Consumer;

public class ScenarioRunner {

    private HttpClient httpClient;
    private Scenario scenario;
    private Table<Integer, Integer, ValidatedAssertions> results;
    private long executionTime;

    public ScenarioRunner(HttpClient httpClient, Scenario scenario) {
        this.httpClient = httpClient;
        this.scenario = scenario;
        this.results = TreeBasedTable.create();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Table<Integer, Integer, ValidatedAssertions> getResults() {
        return results;
    }

    public void forEach(Consumer<? super Table.Cell<Integer, Integer, ValidatedAssertions>> consumer){
        results.cellSet().forEach(consumer);
    }

    public void runScenario() throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < scenario.getLoop(); i++) {
            List<Step> steps = scenario.getSteps();
            for (Step step : steps) {
                for (int j = 0; j < step.getLoop(); j++) {
                    ValidatedAssertions validatedAssertions = runStep(step);
                    results.put(i, j, validatedAssertions);
                    if (validatedAssertions.isInvalid() && !scenario.getIgnoreStepFailures()) {
                        throw new ValidationException(validatedAssertions);
                    }
                }
            }
        }
        long executionTime = System.currentTimeMillis()-start;
        setExecutionTime(executionTime);
    }

    private ValidatedAssertions runStep(Step step) {
        try {
            HttpUriRequest httpRequest = step.getHttpRequest();
            return getValidatedAssertions(step, httpRequest);
        } catch (Exception e) {
            return new ValidatedAssertions(Validation.invalid(io.vavr.collection.List.of("Error: "+ e.getMessage())));
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

}
