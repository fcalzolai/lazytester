package com.lloyds.ymlmodel;

import org.junit.Test;

public class StepsTest {


    private static final String SCENARIO = "steps: \n" +
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

    @Test
    public void stepCreation() {
        Utils.parse(SCENARIO, Steps.class);
    }

}