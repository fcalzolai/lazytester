package com.lloyds.model;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
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
        testIllegalStateException(() -> step.getBody());
        testIllegalStateException(() -> step.getOperation());
        testIllegalStateException(() -> step.getLoop());
    }

    @Test
    public void testBasicGets(){
        Step step = Step.getStepBuilder().setName(STEP1).build();
        testBasicGet(step);
    }

    @Test
    public void testGets(){
        Step parent = Step.getStepBuilder().setName(PARENT).build();
        Step child = Step.getStepBuilder().setName(CHILD).setParent(parent).build();
        testBasicGet(child);
    }

    @Test
    public void testBasicGetName(){
        Step step = Step.getStepBuilder().setName(STEP1).build();
        Assert.assertEquals(STEP1, step.getName());
    }

    @Test
    public void testSets(){
        Step parent = Step.getStepBuilder().setName(PARENT).setUrl(URL1).build();
        Step.StepBuilder stepBuilder = Step.getStepBuilder().setName(CHILD).setParent(parent);

        Assert.assertEquals(URL1, stepBuilder.build().getUrl());

        stepBuilder.setUrl(URL2);
        Assert.assertEquals(URL2, stepBuilder.build().getUrl());
    }

    @Test
    public void testFullUrl(){
        Map<String, String> param = ImmutableMap.<String, String>builder()
                .put("q", "lbg")
                .put("aq", "f")
                .build();
        String url = "http://www.google.com/search";
        Step step = Step.getStepBuilder()
                .setName(STEP1)
                .setUrl(url)
                .setParams(param)
                .build();

        String expectedRes1 = "http://www.google.com/search?q=lbg&aq=f";
        String expectedRes2 = "http://www.google.com/search?aq=f&q=lbg";
        String fullUrl = step.getFullUrl();

        Assert.assertTrue(fullUrl.equals(expectedRes1) || fullUrl.equals(expectedRes2));
    }

}