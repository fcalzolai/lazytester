package com.lazytester.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeatureTest {

    private static final String FEATURE_1 =
            "steps: \n" +
            "   - &google \n" +
            "     name: google\n" +
            "     loop: 3\n" +
            "     url: http://www.google.com\n" +
            "   - name: getGoogle\n" +
            "     operation: GET\n" +
            "     <<: *google\n" +
            "   - name: postGoogle\n" +
            "     operation: POST\n" +
            "     loop: 5\n" +
            "     <<: *google\n" +
            "\n" +
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
            "         - postYahoo\n" +
            "";

    @Test
    public void featureCreation() {
        Feature parse = Utils.parse(FEATURE_1, Feature.class);
        assertNotNull(parse);

        List<Step> steps = parse.getSteps();
        assertEquals(3, steps.size());
        assertNull(steps.get(0).getOperation());
        assertEquals(Operation.GET, steps.get(1).getOperation());
        assertNotNull(steps.get(1).getUrl());

        assertEquals(Operation.POST, steps.get(2).getOperation());
        assertNotNull(steps.get(2).getUrl());

        List<Scenario> scenarios = parse.getScenarios();
        assertEquals(2, scenarios.size());
        assertEquals(2, scenarios.get(0).getSteps().size());
        assertEquals(3, scenarios.get(1).getSteps().size());
    }
}