package com.lloyds.ymlmodel;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import io.vavr.control.Validation;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class FeatureRunner {

    private HttpClient httpClient;
    private Feature feature;
    private Table<Integer, Integer, ValidatedAssertions> results;
    private long executionTime;

    public FeatureRunner(HttpClient httpClient, Feature feature) {
        this.httpClient = httpClient;
        this.feature = feature;
        this.results = TreeBasedTable.create();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public Feature getFeature() {
        return feature;
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
        for (ScenarioWrapper scenarioWrapper : feature.getScenarios()){
            Scenario scenario = scenarioWrapper.getScenario();
            for (int i = 0; i < scenario.getLoop(); i++) {
                List<Step> steps = feature.getSteps(scenario);
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
        }

        long executionTime = System.currentTimeMillis()-start;
        setExecutionTime(executionTime);
    }

    private ValidatedAssertions runStep(Step step) {
        try {
            HttpUriRequest httpRequest = step.getHttpRequest();
            ValidatedAssertions validatedAssertions = getValidatedAssertions(step, httpRequest);
            return validatedAssertions;
        } catch (Exception e) {
            return new ValidatedAssertions(Validation.invalid(io.vavr.collection.List.of("Error: "+ e.getMessage())));
        }
    }

    private ValidatedAssertions getValidatedAssertions(Step step, HttpUriRequest httpRequest) throws IOException {
        long start = System.currentTimeMillis();
        HttpResponse response = httpClient.execute(httpRequest);
        long executionTime = System.currentTimeMillis()-start;
        Assertions assertions = step.getAssertions();
        if (assertions != null) {
            Validator validator = new Validator(assertions, response);
            ValidatedAssertions validated = validator.validate();
            validated.setExecutionTime(executionTime);
            return validated;
        } else {
            return ValidatedAssertions.EMPTY;
        }
    }

}
