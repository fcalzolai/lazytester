package com.lazytester;

import com.lazytester.model.ModelFeature;
import com.lazytester.model.Step;
import com.lazytester.model.Steps;
import com.lazytester.model.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class ParseYmlFileTest {

    private static final String STEPS = "yaml/model/steps.yaml";
    private static final String ANCHOR = "yaml/model/anchor.yaml";
    private static final String SIMPLE = "yaml/model/simple.yaml";
    private static final String FEATURE = "yaml/model/feature.yaml";
    private static final String INCLUDE = "yaml/model/include.yaml";
    private static final String INCLUDE_EXT = "yaml/model/featureExt1.yaml";
    private static final String MALFORMATTED = "yaml/model/malformattedFeature.yaml";

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
        ModelFeature modelFeature = Utils.parse(file, ModelFeature.class);
        Assert.assertEquals(2, modelFeature.getScenarios().size());
        Assert.assertEquals(3, modelFeature.getSteps().size());
    }

    @Test
    public void include() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(INCLUDE);
        ModelFeature modelFeature = Utils.parse(file, ModelFeature.class);
        Assert.assertEquals(1, modelFeature.getScenarios().size());
        Assert.assertEquals(1, modelFeature.getSteps().size());
    }

    @Test
    public void includeExtended() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(INCLUDE_EXT);
        ModelFeature modelFeature = Utils.parse(file, ModelFeature.class);
        Assert.assertEquals(1, modelFeature.getScenarios().size());
        Assert.assertEquals(1, modelFeature.getSteps().size());
    }
}
