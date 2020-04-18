package com.lazytester.runner;

import org.junit.runner.RunWith;

@YamlUnitTestCase(

        // Feature examples using both glob and regex:
        // "glob:**/yaml/**/*.yaml"
        // "glob:**/*.yaml"
        // "glob:**/runner/*.yaml"
        // "regex:.*/runner/.*yaml"
        // ".*/runner/.*yaml"

//        features = {".*runner.*\\.yaml"}
//        features = {".*/runner/feature.yaml"}
        features = {".*/runner/.*yaml"}
)
@RunWith(AnnotationRunner.class)
public class AnnotationRunnerTest {


}