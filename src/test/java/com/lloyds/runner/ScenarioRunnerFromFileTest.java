package com.lloyds.runner;

import com.lloyds.model.Scenario;
import com.lloyds.model.ScenariosResult;
import com.lloyds.utils.Utils;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

public class ScenarioRunnerFromFileTest {

    private static final String PATH = "lazy/jsonPlaceHolder.lz";

    @Test
    public void testJsonPlaceHolder() throws IOException {
        LinkedList<Scenario> scenarios = Utils.createScenarioFromResource(PATH);

        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        ScenariosResult results = scenarioRunner.getScenariosResult();
        results.forEachScenario(
                (scenario, sr) -> sr.forEach(cell -> {
                    System.out.println(scenario + "["+results.get(scenario).getExecutionTime()+" ms] --> " + cell);
                    Assert.assertTrue(cell.getValue().isValid());
                }));
        System.out.println(results.getExecutionTime() + " ms");
    }

}