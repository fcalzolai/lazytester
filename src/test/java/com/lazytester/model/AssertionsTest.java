package com.lazytester.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionsTest {

    private static final String ASSERTION_STATUS = "status: 201 \n";

    private static final String ASSERTION_HEADERS = "headers: \n" +
            "   key1: val1\n" +
            "   key2: val2\n" +
            "   key3: val3\n";

    private static final String ASSERTION_BODY = "body: \n" +
            "   key1: val1\n" +
            "   key2: val2\n" +
            "   key3: val3\n";

    @Test
    public void assertionOnStatus() {
        Assertions assertions = Utils.parse(ASSERTION_STATUS, Assertions.class);
        assertEquals((Integer) 201, assertions.getStatus());
    }

    @Test
    public void assertionOnHeaders() {
        Assertions assertions = Utils.parse(ASSERTION_HEADERS, Assertions.class);
        assertEquals(3, assertions.getHeaders().size());
        assertEquals(0, assertions.getBody().size());
    }

    @Test
    public void assertionOnBody() {
        Assertions assertions = Utils.parse(ASSERTION_BODY, Assertions.class);
        assertEquals(0, assertions.getHeaders().size());
        assertEquals(3, assertions.getBody().size());
    }
}