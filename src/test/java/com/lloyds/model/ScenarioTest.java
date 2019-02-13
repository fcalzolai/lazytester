package com.lloyds.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Supplier;

public class ScenarioTest {

    private static final String CHILD = "child";
    private static final String SCENARIO1 = "step1";
    private static final Integer LOOP1 = 1;
    private static final Integer LOOP2 = 2;

    private <T> void testIllegalStateException(Supplier<T> supplier){
        try {
            T t = supplier.get();
            Assert.fail("It should fail with an IllegalStateException. Instead it gets the object " + t);
        } catch (IllegalStateException ex){

        } catch (Exception ex) {
            Assert.fail("Unexpected exception: "+ ex);
        }
    }

    private void testBasicGet(Scenario scenario) {
        Assert.assertFalse(scenario.getIgnoreStepFailures());
        Assert.assertEquals(Integer.valueOf(1), scenario.getLoop());
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