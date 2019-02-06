package com.lloyds.runner;

import com.lloyds.model.Scenario;
import sun.net.www.http.HttpClient;

public class ScenarioRunner {

    private HttpClient httpClient;
    private Scenario scenario;

    public ScenarioRunner(HttpClient httpClient, Scenario scenario) {
        this.httpClient = httpClient;
        this.scenario = scenario;
    }

    public void runAll(){
        for (int i = 0; i < scenario.getLoop(); i++) {

        }
    }
}
