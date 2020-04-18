package com.lazytester.runner;

import com.google.common.collect.Table;
import com.lazytester.validator.Validator;
import com.lazytester.model.Assertions;
import com.lazytester.model.ModelFeature;
import com.lazytester.model.Scenario;
import com.lazytester.model.ScenarioWrapper;
import com.lazytester.model.Step;
import com.lazytester.validator.ValidatedAssertions;
import com.lazytester.validator.ValidationException;
import io.vavr.control.Validation;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class FeatureRunner {

    //TODO Add log

    public static final ValidatedAssertions EMPTY = new ValidatedAssertions(Validation.valid(null));

    private HttpClient httpClient; //TODO use react WebCLient
    private ModelFeature modelFeature;
    private FeatureResult results;
    private HashMap<Assertions, Validator> validators;
    private long executionTime;

    public FeatureRunner(HttpClient httpClient, ModelFeature modelFeature) {
        this.httpClient = httpClient;
        this.modelFeature = modelFeature;
        this.results = new FeatureResult();
        this.validators = new HashMap<>();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public ModelFeature getModelFeature() {
        return modelFeature;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public FeatureResult getResults() {
        return results;
    }

    public void forEach(Consumer<? super Table.Cell<Integer, Integer, ValidatedAssertions>> consumer){
        results.cellSet().forEach(consumer);
    }

    public void runFeature() throws IOException {
        long start = System.currentTimeMillis();
        for (ScenarioWrapper scenarioWrapper : modelFeature.getScenarios()){
            Scenario scenario = scenarioWrapper.getScenario();
            for (int i = 0; i < scenario.getLoop(); i++) {
                List<Step> steps = modelFeature.getSteps(scenario);
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

        setExecutionTime(System.currentTimeMillis()-start);
    }

    private ValidatedAssertions runStep(Step step) {
        try {
            HttpUriRequest httpRequest = step.getHttpRequest();
            return getValidatedAssertions(step, httpRequest);
        } catch (Exception e) {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            return new ValidatedAssertions(Validation.invalid(io.vavr.collection.List.of("Error: "+ stackTrace)));
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
                Validator validator = getOrCreateValidator(assertions);
                ValidatedAssertions validated = validator.validate(response);
                validated.setExecutionTime(execTime);
                return validated;
            } else {
                return EMPTY;
            }
        } finally {
            if(entity != null) {
                EntityUtils.consume(entity);
            }
        }
    }

    private Validator getOrCreateValidator(Assertions assertions) {
        return validators.computeIfAbsent(assertions, Validator::new);
    }
}
