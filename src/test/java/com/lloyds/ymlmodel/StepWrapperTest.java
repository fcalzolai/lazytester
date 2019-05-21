package com.lloyds.ymlmodel;

import org.junit.Test;

public class StepWrapperTest {

    private static final String STEP_1 = "step:\n" +
            "   name: google\n" +
            "   loop: 3\n" +
            "   url: http://www.google.com\n" +
            "   operation: POST";

    private static final String STEP_2 = "step:\n" +
            "   name: google\n" +
            "   loop: 3\n" +
            "   url: http://www.google.com\n" +
            "   operation: DELETE";

    private static final String STEP_3 = "step:\n" +
            "   name: google\n" +
            "   url: http://www.google.com\n";

    @Test
    public void stepCreation() {
        Utils.parse(STEP_1, StepWrapper.class);
        Utils.parse(STEP_2, StepWrapper.class);
        Utils.parse(STEP_3, StepWrapper.class);
    }

}