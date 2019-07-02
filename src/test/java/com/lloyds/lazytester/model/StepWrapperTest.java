package com.lloyds.lazytester.model;

import com.lloyds.lazytester.Utils;
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
        com.lloyds.lazytester.Utils.parseStringAs(STEP_1, StepWrapper.class);
        com.lloyds.lazytester.Utils.parseStringAs(STEP_2, StepWrapper.class);
        Utils.parseStringAs(STEP_3, StepWrapper.class);
    }

}