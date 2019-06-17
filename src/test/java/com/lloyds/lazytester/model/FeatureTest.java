package com.lloyds.lazytester.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FeatureTest {

    private static final String FEATURE_1 = "steps: \n" +
            "   - step: &google \n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "   - step:\n" +
            "       name: getGoogle\n" +
            "       operation: GET\n" +
            "       <<: *google\n" +
            "   - step:\n" +
            "       name: postGoogle\n" +
            "       operation: POST\n" +
            "       loop: 5\n" +
            "       <<: *google\n" +
            "\n" +
            "scenarios:\n" +
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
            "           - postYahoo\n" +
            "";

    @Test
    public void featureCreation() {
        Feature parse = Utils.parse(FEATURE_1, Feature.class);
        Assert.assertNotNull(parse);

        List<StepWrapper> steps = parse.getSteps();
        Assert.assertEquals(3, steps.size());
        Assert.assertNull(steps.get(0).getStep().getOperation());
        Assert.assertEquals(Operation.GET, steps.get(1).getStep().getOperation());
        Assert.assertNotNull(steps.get(1).getStep().getUrl());

        Assert.assertEquals(Operation.POST, steps.get(2).getStep().getOperation());
        Assert.assertNotNull(steps.get(2).getStep().getUrl());

        List<ScenarioWrapper> scenarios = parse.getScenarios();
        Assert.assertEquals(2, scenarios.size());
        Assert.assertEquals(2, scenarios.get(0).getScenario().getSteps().size());
        Assert.assertEquals(3, scenarios.get(1).getScenario().getSteps().size());
    }
}