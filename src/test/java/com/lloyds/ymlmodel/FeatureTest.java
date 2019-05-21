package com.lloyds.ymlmodel;

import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

    private static final String FEATURE_1 = "steps: \n" +
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
            "\n" +
            "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 2\n" +
            "       steps:\n" +
            "           - getGoogle\n" +
            "           - postGoogle\n" +
            "   - scenario: \n" +
            "       name: As simple POST request response\n" +
            "       loop: 5\n" +
            "       steps:\n" +
            "           - getYahoo\n" +
            "           - deleteYahoo\n" +
            "           - postYahoo\n" +
            "";


    @Test
    public void featureCreation() {
        Feature parse = Utils.parse(FEATURE_1, Feature.class);
        Assert.assertNotNull(parse);
    }

}