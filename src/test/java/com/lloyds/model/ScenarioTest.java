package com.lloyds.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class ScenarioTest {

    private static final String CHILD = "child";
    private static final String SCENARIO1 = "step1";
    private static final Integer LOOP1 = 1;
    private static final Integer LOOP2 = 2;

    private void testBasicGet(Scenario scenario) {
        Assert.assertFalse(scenario.getIgnoreStepFailures());
        Assert.assertEquals(Scenario.DEFAULT_LOOP, scenario.getLoop());
        Assert.assertEquals(new LinkedList<>(), scenario.getSteps());
    }

    @Test
    public void testBasicGets(){
        Scenario step = Scenario.getScenarioBuilder().setName(SCENARIO1).build();
        testBasicGet(step);
    }

    @Test
    public void testGets(){
        Scenario child = Scenario
                .getScenarioBuilder()
                .setName(CHILD)
                .build();
        testBasicGet(child);
    }

    @Test
    public void testBasicGetName(){
        Scenario step = Scenario.getScenarioBuilder().setName(SCENARIO1).build();
        Assert.assertEquals(SCENARIO1, step.getName());
    }

    @Test
    public void testGet(){
        Scenario child = Scenario
                .getScenarioBuilder()
                .setName(CHILD)
                .build();
        testBasicGet(child);
    }

    @Test
    public void testSets(){
        Scenario.ScenarioBuilder childBuilder = Scenario
                .getScenarioBuilder()
                .setName(CHILD);

        Assert.assertEquals(LOOP1, childBuilder.build().getLoop());

        Assert.assertEquals(LOOP2, childBuilder.setLoop(LOOP2).build().getLoop());
    }

}