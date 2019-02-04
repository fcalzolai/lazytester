package com.lloyds.composite;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

public class ScenarioTest {

    private static final String PARENT = "parent";
    private static final String CHILD = "child";

    private static final String URL1 = "url";
    private static final String URL2 = "url2";
    private static final String SCENARIO1 = "step1";
    private static final Integer LOOP1 = 1;
    private static final Integer LOOP2 = 2;

    private <T> void testIllegalStateException(Supplier<T> supplier){
        try {
            supplier.get();
            Assert.fail("It should fail with an IllegalStateException");
        } catch (IllegalStateException ex){

        } catch (Exception ex) {
            Assert.fail("Unexpected exception: "+ ex);
        }
    }

    private void testBasicGet(Scenario scenario) {
        testIllegalStateException(() -> scenario.getIgnoreStepFailures());
        testIllegalStateException(() -> scenario.getLoop());
        testIllegalStateException(() -> scenario.getSteps());
    }

    @Test
    public void testBasicGets(){
        Scenario step = Scenario.getScenarioBuilder().setName(SCENARIO1).build();
        testBasicGet(step);
    }

    @Test
    public void testGets(){
        Scenario parent = Scenario.getScenarioBuilder().setName(PARENT).build();
        Scenario child = Scenario
                .getScenarioBuilder()
                .setName(CHILD)
                .setExtend(parent)
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
        Scenario parent = Scenario.getScenarioBuilder().setName(PARENT).build();
        Scenario child = Scenario
                .getScenarioBuilder()
                .setName(CHILD)
                .setExtend(parent)
                .build();
        testBasicGet(child);
    }

    @Test
    public void testSets(){
        Scenario parentBuilder = Scenario
                .getScenarioBuilder()
                .setName(PARENT)
                .setLoop(LOOP1)
                .build();

        Scenario.ScenarioBuilder childBuilder = Scenario
                .getScenarioBuilder()
                .setName(CHILD)
                .setExtend(parentBuilder);

        Assert.assertEquals(LOOP1, childBuilder.build().getLoop());

        Assert.assertEquals(LOOP2, childBuilder.setLoop(LOOP2).build().getLoop());
    }

}