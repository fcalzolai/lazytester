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
    @JsonTestCase("simple_test.json")
    public void testGet() throws Exception {

    }

    @Test
    @JsonTestCase("extFileDependency.json")
    public void testExtFileDependency() throws Exception {

    }
}
