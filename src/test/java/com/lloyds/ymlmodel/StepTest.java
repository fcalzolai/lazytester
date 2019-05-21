package com.lloyds.ymlmodel;

import org.junit.Test;

public class StepTest {

    private static final String STEP_1 = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: POST";

    private static final String STEP_2 = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: DELETE";

    private static final String STEP_3 = "name: google\n" +
            "url: http://www.google.com\n";

    @Test
    public void stepCreation() {
        Utils.parse(STEP_1, Step.class);
        Utils.parse(STEP_2, Step.class);
        Utils.parse(STEP_3, Step.class);
    }
}