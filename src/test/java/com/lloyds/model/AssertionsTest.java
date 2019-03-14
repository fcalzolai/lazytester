package com.lloyds.model;

import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;

public class AssertionsTest {

    @Test
    public void getStatus() {
        Assertions parentAssertions = Assertions.getBuilder()
                .setStatus(201)
                .build();

        Step parent = Step.getStepBuilder()
                .setName("parent")
                .setAssertions(parentAssertions)
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .build();

        Step.StepBuilder childBuilder = Step.getStepBuilder()
                .setOperation(HttpGet.METHOD_NAME)
                .setUrl("")
                .setName("child");

        Step child = childBuilder.build();
        Assert.assertFalse(child
                .getAssertions()
                .getStatus().isPresent());

        child = childBuilder.setParent(parent).build();
        Assert.assertEquals(Integer.valueOf(201), child
                .getAssertions()
                .getStatus().get());

        Assertions childAssertions = Assertions.getBuilder()
                .setStatus(202)
                .build();

        child = childBuilder.setAssertions(childAssertions).build();
        Assert.assertEquals(Integer.valueOf(202), child
                .getAssertions()
                .getStatus().get());
    }
}