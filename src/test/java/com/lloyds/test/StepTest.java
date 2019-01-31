package com.lloyds.test;

import com.lloyds.composite.Step;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Supplier;

public class StepTest {

    private static final String PARENT = "parent";
    private static final String CHILD = "child";

    private static final String URL1 = "url";
    private static final String URL2 = "url2";
    private static final String STEP1 = "step1";

    private <T> void testIllegalStateException(Supplier<T> supplier){
        try {
            supplier.get();
            Assert.fail("It should fail with an IllegalStateException");
        } catch (IllegalStateException ex){

        } catch (Exception ex) {
            Assert.fail("Unexpected exception: "+ ex);
        }
    }

    private void testBasicGet(Step step) {
        testIllegalStateException(() -> step.getAssertions());
        testIllegalStateException(() -> step.getUrl());
        testIllegalStateException(() -> step.getRequest());
        testIllegalStateException(() -> step.getOperation());
        testIllegalStateException(() -> step.getLoop());
    }

    @Test
    public void testBasicGets(){
        Step step = new Step(STEP1);
        testBasicGet(step);
    }

    @Test
    public void testGets(){
        Step parent = new Step(PARENT);
        Step child = new Step(CHILD);
        child.setExtend(parent);
        testBasicGet(child);
    }

    @Test
    public void testBasicGetName(){
        Step step = new Step(STEP1);
        Assert.assertEquals(STEP1, step.getName());
    }

    @Test
    public void testGet(){
        Step parent = new Step(PARENT);
        Step child = new Step(CHILD);
        child.setExtend(parent);
        testBasicGet(child);
    }

    @Test
    public void testSets(){
        Step parent = new Step(PARENT);
        Step child = new Step(CHILD);
        child.setExtend(parent);

        parent.setUrl(URL1);
        Assert.assertEquals(URL1, child.getUrl());

        child.setUrl(URL2);
        Assert.assertEquals(URL2, child.getUrl());
    }
}
