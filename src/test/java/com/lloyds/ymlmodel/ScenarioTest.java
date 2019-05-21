package com.lloyds.ymlmodel;

import org.junit.Assert;
import org.junit.Test;

public class ScenarioTest {

    private static final String SCENARIO_1 = "name: As simple GET request response\n" +
            "loop: 2\n" +
            "ignoreStepFailures: true\n" +
            "steps:\n" +
            "   - getGoogle\n" +
            "   - postGoogle\n";

    @Test
    public void scenarioCreation() {
        Scenario scenario = Utils.parse(SCENARIO_1, Scenario.class);
        Assert.assertEquals("As simple GET request response", scenario.getName());
        Assert.assertEquals((Integer) 2, scenario.getLoop());
        Assert.assertEquals(true, scenario.getIgnoreStepFailures());
        Assert.assertEquals(2, scenario.getSteps().size());
    }

}