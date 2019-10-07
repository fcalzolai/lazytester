package com.lazytester.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ScenariosTest {

    private static final String SCENARIOS_1 = "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 2\n" +
            "       steps:\n" +
            "           - getGoogle\n" +
            "           - postGoogle\n" +
            "   - scenario: \n" +
            "       name: As simple POST request response\n" +
            "       loop: 5\n" +
            "       steps:\n" +
            "           - getYahoo\n" +
            "           - deleteYahoo\n" +
            "           - postYahoo\n"
            ;

    @Test
    public void scenariosCreation() {
        Scenarios scenarios = Utils.parse(SCENARIOS_1, Scenarios.class);
        Assert.assertNotNull(scenarios);
        List<ScenarioWrapper> scenarioWrappers = scenarios.getScenarios();
        Assert.assertEquals(2, scenarioWrappers.size());

        Scenario scenario_0 = scenarioWrappers.get(0).getScenario();
        Assert.assertEquals("As simple GET request response", scenario_0.getName());
        Assert.assertEquals((Integer) 2, scenario_0.getLoop());
        Assert.assertEquals(2, scenario_0.getSteps().size());

        Scenario scenario_1 = scenarioWrappers.get(1).getScenario();
        Assert.assertEquals("As simple POST request response", scenario_1.getName());
        Assert.assertEquals((Integer) 5, scenario_1.getLoop());
        Assert.assertEquals(3, scenario_1.getSteps().size());
    }

}