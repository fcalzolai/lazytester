package com.lloyds.lazytester.model;

import org.junit.Assert;
import org.junit.Test;

public class AssertionsTest {

    private static final String ASSERTION_1 = "status: 201 \n";

    @Test
    public void stepCreation() {
        Assertions parse = Utils.parse(ASSERTION_1, Assertions.class);
        Assert.assertEquals((Integer) 201, parse.getStatus());
    }

}