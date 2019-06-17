package com.lloyds.lazytester;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.lloyds.lazytester.model.Assertions;
import com.lloyds.lazytester.model.Feature;
import com.lloyds.lazytester.model.Scenario;
import com.lloyds.lazytester.model.ScenarioWrapper;
import com.lloyds.lazytester.model.Step;
import com.lloyds.lazytester.validator.ValidatedAssertions;
import com.lloyds.lazytester.validator.ValidationException;
import com.lloyds.lazytester.validator.Validator;
import io.vavr.control.Validation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

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

    public void runFeature() throws IOException {
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

        System.out.println(results);

        setExecutionTime(System.currentTimeMillis()-start);
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
        HttpEntity entity = null;
        try {
            long start = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpRequest);
            entity = response.getEntity();
            long execTime = System.currentTimeMillis() - start;
            Assertions assertions = step.getAssertions();
            if (assertions != null) {
                Validator validator = new Validator(assertions, response);
                ValidatedAssertions validated = validator.validate();
                validated.setExecutionTime(execTime);
                return validated;
            } else {
                return ValidatedAssertions.EMPTY;
            }
        } finally {
            if(entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }

}
