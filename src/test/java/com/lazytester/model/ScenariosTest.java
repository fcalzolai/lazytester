package com.lazytester.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScenariosTest {

    private static final String SCENARIOS_1 =
            "scenarios:\n" +
            "   - name: As simple GET request response\n" +
            "     loop: 2\n" +
            "     steps:\n" +
            "         - getGoogle\n" +
            "         - postGoogle\n" +
            "   - name: As simple POST request response\n" +
            "     loop: 5\n" +
            "     steps:\n" +
            "         - getYahoo\n" +
            "         - deleteYahoo\n" +
            "         - postYahoo\n"
            ;

    @Test
    public void scenariosCreation() {
        Scenarios scenarios = Utils.parse(SCENARIOS_1, Scenarios.class);
        assertNotNull(scenarios);
        List<Scenario> scenarioList = scenarios.getScenarios();
        assertEquals(2, scenarioList.size());

        Scenario scenario_0 = scenarioList.get(0);
        assertEquals("As simple GET request response", scenario_0.getName());
        assertEquals((Integer) 2, scenario_0.getLoop());
        assertEquals(2, scenario_0.getSteps().size());

        Scenario scenario_1 = scenarioList.get(1);
        assertEquals("As simple POST request response", scenario_1.getName());
        assertEquals((Integer) 5, scenario_1.getLoop());
        assertEquals(3, scenario_1.getSteps().size());
    }

}