package com.lloyds.lazytester.model;

import com.lloyds.lazytester.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

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

    private static final String STEP_4 = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: POST\n" +
            "params:\n" +
            "   postId: 1\n" +
            "   id: 2\n" +
            "\n"
            ;

    private static final String STEP_5 = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: DELETE\n" +
            "headers:\n" +
            "   user-agent: \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\"\n" +
            "   accept-encoding: \"gzip, deflate, br\"\n" +
            "\n"
            ;

    private static final String STEP_6 = "name: google\n" +
            "loop: 3\n" +
            "url: http://www.google.com\n" +
            "operation: DELETE\n" +
            "headers:\n" +
            "   user-agent: \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\"\n" +
            "   accept-encoding: \"gzip, deflate, br\"\n" +
            "params:\n" +
            "   postId: 1\n" +
            "   id: 2\n" +
            "assertions:\n" +
            "   status: 201 \n" +
            "\n"
            ;

    @Test
    public void stepCreation() {
        com.lloyds.lazytester.Utils.parseStringAs(STEP_1, Step.class);
        com.lloyds.lazytester.Utils.parseStringAs(STEP_2, Step.class);
        com.lloyds.lazytester.Utils.parseStringAs(STEP_3, Step.class);
    }

    @Test
    public void stepWithParamsCreation() throws UnsupportedEncodingException {
        Step parse = com.lloyds.lazytester.Utils.parseStringAs(STEP_4, Step.class);
        String expectedUrl = "http://www.google.com?postId=1&id=2";
        Assert.assertEquals(expectedUrl, parse.getFullUrl());
        Assert.assertNotNull(parse.getHttpRequest());
        Assert.assertEquals(2, parse.getParams().size());
        Assert.assertEquals(0, parse.getHeaders().size());
        Assert.assertNull(parse.getAssertions());
    }

    @Test
    public void stepWithHeadersCreation() {
        Step parse = com.lloyds.lazytester.Utils.parseStringAs(STEP_5, Step.class);
        Assert.assertEquals(2, parse.getHeaders().size());
        Assert.assertEquals(0, parse.getParams().size());
        Assert.assertNull(parse.getAssertions());
    }

    @Test
    public void stepWithHeadersAndParamsCreation() {
        Step parse = Utils.parseStringAs(STEP_6, Step.class);
        Assert.assertNotNull(parse.getHeaders());
        Assert.assertEquals(2, parse.getParams().size());
        Assert.assertNotNull(parse.getAssertions());
    }
}