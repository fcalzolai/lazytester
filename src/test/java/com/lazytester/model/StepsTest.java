package com.lazytester.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepsTest {


    private static final String STEPS_1 =
            "steps: \n" +
            "   - name: google\n" +
            "     loop: 3\n" +
            "     url: http://www.google.com\n" +
            "     operation: POST\n" +
            "   - name: google\n" +
            "     loop: 3\n" +
            "     url: http://www.google.com\n" +
            "     operation: DELETE\n" +
            "   - name: google\n" +
            "     url: http://www.google.com\n" +
            "";

    private static final String GOOGLE = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" ;

    private static final String GET_GOOGLE = "name: getGoogle\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: GET\n" +
            "" ;

    private static final String POST_GOOGLE = "name: postGoogle\n" +
            "loop: 5\n" +
            "url: http://www.google.com\n" +
            "operation: POST\n" +
            "" ;

    private static final String STEPS_2 = "steps: \n" +
            "   - &google \n" +
            "     name: google\n" +
            "     loop: 3\n" +
            "     url: http://www.google.com\n" +
            "   - name: getGoogle\n" +
            "     operation: GET\n" +
            "     <<: *google\n" +
            "   - name: postGoogle\n" +
            "     operation: POST\n" +
            "     loop: 5\n" +
            "     <<: *google\n" +
            "";

    @Test
    public void stepsCreation() {
        Utils.parse(STEPS_1, Steps.class);
    }

    @Test
    public void stepsWithAnchorCreation() {
        Steps steps = Utils.parse(STEPS_2, Steps.class);
        Step google = steps.getSteps().get(0);
        Step getGoogle = steps.getSteps().get(1);
        Step postGoogle = steps.getSteps().get(2);

        assertEquals(Utils.parse(GOOGLE, Step.class), google);
        assertEquals(Utils.parse(GET_GOOGLE, Step.class), getGoogle);
        assertEquals(Utils.parse(POST_GOOGLE, Step.class), postGoogle);
    }
}