package com.lloyds;

import com.lloyds.lazytester.model.Feature;
import com.lloyds.lazytester.model.Steps;
import com.lloyds.lazytester.model.Utils;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;

public class ParseYmlFileTest {

    private static final String STEPS = "yaml/steps.yaml";
    private static final String ANCHOR = "yaml/anchor.yaml";
    private static final String SIMPLE = "yaml/simple.yaml";
    private static final String FEATURE = "yaml/feature.yaml";

    @Test
    public void steps() throws FileNotFoundException {
        URL is = ParseYmlFileTest.class.getClassLoader().getResource(STEPS);
        Steps parse = Utils.parse(is, Steps.class);
    }

    @Test
    public void anchor() throws FileNotFoundException {
        URL is = ParseYmlFileTest.class.getClassLoader().getResource(ANCHOR);
        Steps parse = Utils.parse(is, Steps.class);
    }

    @Test
    public void simple() throws FileNotFoundException {
        URL is = ParseYmlFileTest.class.getClassLoader().getResource(SIMPLE);
        Steps parse = Utils.parse(is, Steps.class);
    }

    @Test
    public void scenario() throws FileNotFoundException {
        URL is = ParseYmlFileTest.class.getClassLoader().getResource(FEATURE);
        Feature parse = Utils.parse(is, Feature.class);
    }
}
