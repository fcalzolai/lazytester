package com.lloyds.lazytester.runner;

import org.junit.runner.RunWith;

@RunWith(AnnotationRunner.class)
@YamlUnitTestCase(
//        features = {".*runner.*\\.yaml"}
        features = {".*/runner/.*yaml"}
)
public class AnnotationRunnerTest {

    // Feature examples using both glob and regex:
    // "glob:**/yaml/**/*.yaml"
    // "glob:**/*.yaml"
    // "glob:**/runner/*.yaml"
    // "regex:.*/runner/.*yaml"
    // ".*/runner/.*yaml"
}