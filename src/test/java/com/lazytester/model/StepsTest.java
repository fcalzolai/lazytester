package com.lazytester.model;

import com.lazytester.event.Utils;
import org.junit.Assert;
import org.junit.Test;

public class StepsTest {


    private static final String STEPS_1 = "steps: \n" +
            "   - step:\n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "       operation: POST\n" +
            "   - step:\n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "       operation: DELETE\n" +
            "   - step:\n" +
            "       name: google\n" +
            "       url: http://www.google.com\n" +
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
            "   - step: &google \n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "   - step:\n" +
            "       name: getGoogle\n" +
            "       operation: GET\n" +
            "       <<: *google\n" +
            "   - step:\n" +
            "       name: postGoogle\n" +
            "       operation: POST\n" +
            "       loop: 5\n" +
            "       <<: *google\n" +
            "";

    @Test
    public void stepsCreation() {
        Utils.parseStringAs(STEPS_1, Steps.class);
    }

    @Test
    public void stepsWithAnchorCreation() {
        Steps steps = Utils.parseStringAs(STEPS_2, Steps.class);
        Step google = steps.getSteps().get(0).getStep();
        Step getGoogle = steps.getSteps().get(1).getStep();
        Step postGoogle = steps.getSteps().get(2).getStep();

        Assert.assertEquals(Utils.parseStringAs(GOOGLE, Step.class), google);
        Assert.assertEquals(Utils.parseStringAs(GET_GOOGLE, Step.class), getGoogle);
        Assert.assertEquals(Utils.parseStringAs(POST_GOOGLE, Step.class), postGoogle);
    }
}