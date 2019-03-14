package com.lloyds.model;

import com.google.common.collect.ImmutableMap;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class StepTest {

    private static final String PARENT = "parent";
    private static final String CHILD = "child";
    private static final String URL1 = "url";
    private static final String URL2 = "url2";
    private static final String STEP1 = "step1";
    private static final Map<String, String> GOOGLE_VALID_QUERY_PARAM = ImmutableMap.<String, String>builder()
            .put("q", "lbg")
            .put("aq", "f")
            .build();

    private void testBasicGet(Step step) {
        Assert.assertFalse(step.getBody().isPresent());
    }

    @Test
    public void testBasicGets(){
        Step step = Step.getStepBuilder()
                .setName(STEP1)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .build();

        testBasicGet(step);
        Assert.assertEquals(Step.DEFAULT_LOOP, step.getLoop());
    }

    @Test
    public void testGets(){
        Step parent = Step.getStepBuilder()
                .setName(PARENT)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .build();

        Step child = Step.getStepBuilder()
                .setName(CHILD)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .setParent(parent)
                .build();

        testBasicGet(child);
        Assert.assertEquals(Step.DEFAULT_LOOP, child.getLoop());
    }

    @Test
    public void testBasicGetName(){
        Step step = Step.getStepBuilder()
                .setName(STEP1)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .build();
        Assert.assertEquals(STEP1, step.getName());
    }

    @Test
    public void testSets(){
        Step.StepBuilder parentBuilder = Step.getStepBuilder()
                .setName(PARENT)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .setUrl(URL1);

        Step.StepBuilder childBuilder = Step.getStepBuilder()
                .setName(CHILD)
                .setParent(parentBuilder.build());

        Assert.assertEquals(URL1, childBuilder.build().getUrl());
        Assert.assertEquals(Step.DEFAULT_LOOP, childBuilder.build().getLoop());

        parentBuilder.setLoop(2);
        childBuilder.setParent(parentBuilder.build());
        Assert.assertEquals(Integer.valueOf(2), childBuilder.build().getLoop());

        childBuilder.setUrl(URL2);
        childBuilder.setLoop(3);
        Assert.assertEquals(Integer.valueOf(3), childBuilder.build().getLoop());
        Assert.assertEquals(URL2, childBuilder.build().getUrl());
    }

    @Test
    public void testFullUrl(){
        String url = "http://www.google.com/search";
        Step step = Step.getStepBuilder()
                .setName(STEP1)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl(url)
                .setParams(GOOGLE_VALID_QUERY_PARAM)
                .build();

        String expectedRes1 = "http://www.google.com/search?q=lbg&aq=f";
        String expectedRes2 = "http://www.google.com/search?aq=f&q=lbg";
        String fullUrl = step.getFullUrl();

        Assert.assertTrue(fullUrl.equals(expectedRes1) || fullUrl.equals(expectedRes2));
    }

    @Test
    public void testParams(){
        Step parent = Step.getStepBuilder()
                .setParams(GOOGLE_VALID_QUERY_PARAM)
                .setUrl("")
                .setOperation(HttpGet.METHOD_NAME)
                .build();

        Step.StepBuilder childBuilder = Step.getStepBuilder().setParent(parent);

        Step child = childBuilder.build();

        Assert.assertEquals(GOOGLE_VALID_QUERY_PARAM, child.getParams());
        ImmutableMap<String, String> params = ImmutableMap.<String, String>builder()
                .put("q", "lbg2")
                .build();

        child = childBuilder
                .setParams(params)
                .build();

        ImmutableMap<String, String> expected = ImmutableMap.<String, String>builder()
                .put("q", "lbg2")
                .put("aq", "f")
                .build();
        Assert.assertEquals(expected, child.getParams());
    }

}