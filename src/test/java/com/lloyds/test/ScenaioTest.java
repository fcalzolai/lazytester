package com.lloyds.test;

import com.lloyds.composite.Scenario;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.function.Supplier;

public class ScenaioTest {

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
        Scenario step = new Scenario(SCENARIO1);
        testBasicGet(step);
    }

    @Test
    public void testGets(){
        Scenario parent = new Scenario(PARENT);
        Scenario child = new Scenario(CHILD);
        child.setExtend(parent);
        testBasicGet(child);
    }

    @Test
    public void testBasicGetName(){
        Scenario step = new Scenario(SCENARIO1);
        Assert.assertEquals(SCENARIO1, step.getName());
    }

    @Test
    public void testGet(){
        Scenario parent = new Scenario(PARENT);
        Scenario child = new Scenario(CHILD);
        child.setExtend(parent);
        testBasicGet(child);
    }

    @Test
    public void testSets(){
        Scenario parent = new Scenario(PARENT);
        Scenario child = new Scenario(CHILD);
        child.setExtend(parent);

        parent.setLoop(LOOP1);
        Assert.assertEquals(LOOP1, child.getLoop());

        child.setLoop(LOOP2);
        Assert.assertEquals(LOOP2, child.getLoop());
    }
}
