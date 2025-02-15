package com.lazytester.model;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

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
        Utils.parse(STEP_1, Step.class);
        Utils.parse(STEP_2, Step.class);
        Utils.parse(STEP_3, Step.class);
    }

    @Test
    public void stepWithParamsCreation() throws UnsupportedEncodingException {
        Step parse = Utils.parse(STEP_4, Step.class);
        String expectedUrl = "http://www.google.com?postId=1&id=2";
        assertEquals(expectedUrl, parse.getFullUrl());
        assertNotNull(parse.getHttpRequest());
        assertEquals(2, parse.getParams().size());
        assertEquals(0, parse.getHeaders().size());
        assertNull(parse.getAssertions());
    }

    @Test
    public void stepWithHeadersCreation() {
        Step parse = Utils.parse(STEP_5, Step.class);
        assertEquals(2, parse.getHeaders().size());
        assertEquals(0, parse.getParams().size());
        assertNull(parse.getAssertions());
    }

    @Test
    public void stepWithHeadersAndParamsCreation() {
        Step parse = Utils.parse(STEP_6, Step.class);
        assertNotNull(parse.getHeaders());
        assertEquals(2, parse.getParams().size());
        assertNotNull(parse.getAssertions());
    }
}