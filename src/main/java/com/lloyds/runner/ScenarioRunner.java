package com.lloyds.runner;

import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ScenarioRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScenarioRunner.class);

    private HttpClient httpClient;
    private Scenario scenario;

    public ScenarioRunner(HttpClient httpClient, Scenario scenario) {
        this.httpClient = httpClient;
        this.scenario = scenario;
    }

    public void runAll() throws IOException {
        for (int i = 0; i < scenario.getLoop(); i++) {
            List<Step> steps = scenario.getSteps();
            for (Step step: steps) {
                for (int j = 0; j < step.getLoop(); j++) {
                    HttpUriRequest httpRequest = step.getHttpRequest();
                    try {
                        HttpResponse response = httpClient.execute(httpRequest);
                        System.out.println(response);
                        HttpEntity entity1 = response.getEntity();
                        EntityUtils.consume(entity1);
                    } catch (IOException e) {
                        if(scenario.getIgnoreStepFailures()){
                            LOGGER.warn("Excpetion ignored.", e);
                        } else {
                            throw e;
                        }
                    }
                }
            }
        }
    }
}
