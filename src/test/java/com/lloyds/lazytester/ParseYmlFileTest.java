package com.lloyds.lazytester;

import com.lloyds.lazytester.model.Feature;
import com.lloyds.lazytester.model.Step;
import com.lloyds.lazytester.model.Steps;
import com.lloyds.lazytester.model.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class ParseYmlFileTest {

    private static final String STEPS = "yaml/steps.yaml";
    private static final String ANCHOR = "yaml/anchor.yaml";
    private static final String SIMPLE = "yaml/simple.yaml";
    private static final String FEATURE = "yaml/feature.yaml";

    @Test
    public void steps() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(STEPS);
        Steps steps = Utils.parse(file, Steps.class);
        Assert.assertEquals(3, steps.getSteps().size());
        List<String> actual = steps.getSteps().stream()
                .map(stepWrapper -> stepWrapper.getStep().getName())
                .collect(Collectors.toList());
        Assert.assertThat(actual, containsInAnyOrder("google", "yahoo", "test"));
    }

    @Test
    public void anchor() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(ANCHOR);
        Steps steps = Utils.parse(file, Steps.class);
        Assert.assertTrue(
                steps.getSteps()
                        .stream()
                        .allMatch(stepWrapper -> stepWrapper.getStep().getName().equals("google"))
        );
        Assert.assertEquals(3, steps.getSteps().get(0).getStep().getLoop().intValue());
        Assert.assertEquals(1, steps.getSteps().get(1).getStep().getLoop().intValue());
    }

    @Test
    public void simple() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(SIMPLE);
        Steps steps = Utils.parse(file, Steps.class);
        Assert.assertEquals(1, steps.getSteps().size());
        Step step = steps.getSteps().get(0).getStep();
        Assert.assertEquals("google", step.getName());
        Assert.assertEquals(3, step.getLoop().intValue());
        Assert.assertEquals("http://www.google.com", step.getUrl());
    }

    @Test
    public void scenario() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(FEATURE);
        Feature feature = Utils.parse(file, Feature.class);
        Assert.assertEquals(2, feature.getScenarios().size());
        Assert.assertEquals(3, feature.getSteps().size());
    }
}
