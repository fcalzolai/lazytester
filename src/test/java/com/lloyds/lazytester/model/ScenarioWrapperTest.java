package com.lloyds.lazytester.model;

import com.lloyds.lazytester.Utils;
import org.junit.Assert;
import org.junit.Test;

public class ScenarioWrapperTest {

    private static final String SCENARIO_1 = "scenario: \n" +
            "   name: As simple GET request response\n" +
            "   loop: 2\n" +
            "   steps:\n" +
            "       - getGoogle\n" +
            "       - postGoogle\n";

    @Test
    public void scenarioWrapperCreation() {
        ScenarioWrapper scenarioWrapper = Utils.parseStringAs(SCENARIO_1, ScenarioWrapper.class);
        Assert.assertNotNull(scenarioWrapper);
        Scenario scenario = scenarioWrapper.getScenario();
        Assert.assertEquals("As simple GET request response", scenario.getName());
        Assert.assertEquals((Integer) 2, scenario.getLoop());
        Assert.assertEquals(2, scenario.getSteps().size());
    }

}