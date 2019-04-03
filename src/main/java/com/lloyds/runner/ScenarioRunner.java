package com.lloyds.runner;

import com.lloyds.model.Scenario;
import com.lloyds.model.ScenariosResult;
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

public class ScenarioRunner {

    //TODO create synchronous and asynchronous Scenario runners
    private HttpClient httpClient;
    private List<Scenario> scenarios;
    private ScenariosResult scenariosResult;

    public ScenarioRunner(HttpClient httpClient, List<Scenario> scenarios) {
        this.httpClient = httpClient;
        this.scenarios = scenarios;
        this.scenariosResult = new ScenariosResult();
    }

    public ScenariosResult getScenariosResult() {
        return scenariosResult;
    }

    public void runScenarios() throws IOException {
        long start = System.currentTimeMillis();
        for (Scenario scenario : scenarios) {
            runScenario(scenario);
        }
        long executionTime = System.currentTimeMillis()-start;
        scenariosResult.setExecutionTime(executionTime);
    }

    private void runScenario(Scenario scenario) throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < scenario.getLoop(); i++) {
            List<Step> steps = scenario.getSteps();
            for (Step step : steps) {
                for (int j = 0; j < step.getLoop(); j++) {
                    ValidatedAssertions validatedAssertions = runStep(step);
                    scenariosResult.put(scenario, i, j, validatedAssertions);
                    if (validatedAssertions.isInvalid() && !scenario.getIgnoreStepFailures()) {
                        throw new ValidationException(validatedAssertions);
                    }
                }
            }
        }
        long executionTime = System.currentTimeMillis()-start;
        scenariosResult.get(scenario).setExecutionTime(executionTime);
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
