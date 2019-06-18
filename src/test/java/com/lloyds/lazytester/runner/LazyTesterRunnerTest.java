package com.lloyds.lazytester.runner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(LazyTesterRunner.class)
public class LazyTesterRunnerTest {

    @Test
    public void shouldAnswerWithTrue(){
        assertTrue( true );
    }

    @Test
    @JsonTestCase("yaml/runner/simple.yaml")
    public void testGet() {

    }
}