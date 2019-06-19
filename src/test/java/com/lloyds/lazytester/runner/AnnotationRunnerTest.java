package com.lloyds.lazytester.runner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AnnotationRunner.class)
public class AnnotationRunnerTest {

    @Test
    public void shouldAnswerWithTrue(){
        assertTrue( true );
    }

    @Test
    @YamlTestCase("yaml/runner/simple.yaml")
    public void testGet() {

    }
}