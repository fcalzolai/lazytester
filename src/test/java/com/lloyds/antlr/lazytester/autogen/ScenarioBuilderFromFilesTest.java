package com.lloyds.antlr.lazytester.autogen;

import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import com.lloyds.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ScenarioBuilderFromFilesTest {

    private static final String PATH = "lazy/jsonPlaceHolder.lz";

    @Test
    public void testJsonPlaceHolder() throws IOException {
        List<Scenario> scenarios = Utils.createScenarioFromResource(PATH);
        Assert.assertEquals(2, scenarios.size());

        Scenario scenario = scenarios.get(0);
        Assert.assertNotNull(scenario);
        Assert.assertEquals("Scenario1", scenario.getName());
        Assert.assertFalse(scenario.getIgnoreStepFailures());
        Assert.assertEquals(2, scenario.getLoop().intValue());
        List<Step> steps = scenario.getSteps();
        Assert.assertNotNull(steps);
        Assert.assertEquals(3, steps.size());

        Step step = steps.get(0);
        Assert.assertTrue(step.getParent().isPresent());
        Assert.assertEquals("post_placeholder1", step.getParent().get().getName());

        step = steps.get(1);
        Assert.assertTrue(step.getParent().isPresent());
        Assert.assertEquals("get_placeholder1", step.getParent().get().getName());
        Assert.assertEquals(2, step.getLoop().intValue());
        Assert.assertEquals("https://jsonplaceholder.typicode.com/posts", step.getUrl());

        step = steps.get(2);
        Assert.assertTrue(step.getParent().isPresent());
        Assert.assertEquals("get_placeholder2", step.getParent().get().getName());
        Assert.assertEquals(10, step.getLoop().intValue());
        Assert.assertEquals("https://jsonplaceholder.typicode.com/posts", step.getUrl());
    }
}