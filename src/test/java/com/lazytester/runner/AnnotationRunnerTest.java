package com.lazytester.runner;

import org.junit.Ignore;
import org.junit.runner.RunWith;

// Feature examples using both glob and regex:
// "glob:**/yaml/**/*.yaml"
// "glob:**/*.yaml"
// "glob:**/runner/*.yaml"
// "regex:.*/runner/.*yaml"
// ".*/runner/.*yaml"
@YamlUnitTestCase(
//        features = {".*runner.*\\.yaml"}
        features = {".*/runner/.*yaml"}
)
@Ignore
@RunWith(AnnotationRunner.class)
public class AnnotationRunnerTest {


}