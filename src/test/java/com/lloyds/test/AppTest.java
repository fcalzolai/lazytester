package com.lloyds.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LazyTesterRunner.class)
public class AppTest {

    @Test
    public void shouldAnswerWithTrue(){
        assertTrue( true );
    }

    @Test
    @JsonTestCase("empty_test.json")
    public void testEmptyJson() throws Exception {

    }

    @Test
    @JsonTestCase("google.json")
    public void testGoogle() throws Exception {

    }
}
