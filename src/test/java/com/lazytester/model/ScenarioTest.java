package com.lazytester.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("As simple GET request response", scenario.getName());
        assertEquals((Integer) 2, scenario.getLoop());
        assertEquals(true, scenario.getIgnoreStepFailures());
        assertEquals(2, scenario.getSteps().size());
    }

}