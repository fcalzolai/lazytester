package com.lloyds.runner;

import com.lloyds.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ScenarioListRunnerFromFileTest {

    private static final String PATH_1 = "lazy/jsonPlaceHolder.lz";
    private static final String SCENARIOS_PATH = "lazy/scenarios.lz";

    @Test
    public void testImport() throws IOException {
        List<ScenarioRunner> scenarios = Utils.createScenarioRunnerFromResource(SCENARIOS_PATH);

        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        scenarioListRunner.forEachScenario(sr -> {
            System.out.println(sr + "[" + sr.getExecutionTime() + " ms]");
            sr.forEach(
                    cell -> {
                        System.out.println(sr + " --> " + cell);
                        Assert.assertTrue(cell.getValue().isValid());
                    }
            );
        });
        System.out.println(scenarioListRunner.getExecutionTime() + " ms");
    }

    @Test
    public void testJsonPlaceHolder() throws IOException {
        List<ScenarioRunner> scenarios = Utils.createScenarioRunnerFromResource(PATH_1);

        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        scenarioListRunner.forEachScenario(sr -> {
            System.out.println(sr + "[" + sr.getExecutionTime() + " ms]");
            sr.forEach(
                    cell -> {
                        System.out.println(sr + " --> " + cell);
                        Assert.assertTrue(cell.getValue().isValid());
                    }
            );
        });
        System.out.println(scenarioListRunner.getExecutionTime() + " ms");
    }
}
