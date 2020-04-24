package com.lazytester;

import com.lazytester.model.Feature;
import com.lazytester.model.Step;
import com.lazytester.model.Utils;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParseYmlFileTest {

    private static final String STEPS = "yaml/model/steps.yaml";
    private static final String ANCHOR = "yaml/model/anchor.yaml";
    private static final String SIMPLE = "yaml/model/simple.yaml";
    private static final String FEATURE = "yaml/model/feature.yaml";
    private static final String MALFORMATTED = "yaml/model/malformattedFeature.yaml";

    @Test
    public void steps() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(STEPS);
        Feature feature = Utils.parse(file, Feature.class);
        assertEquals(3, feature.getSteps().size());
        List<String> actual = feature.getSteps().stream()
                .map(stepWrapper -> stepWrapper.getName())
                .collect(Collectors.toList());
        assertThat(actual, containsInAnyOrder("google", "yahoo", "test"));
    }

    @Test
    public void anchor() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(ANCHOR);
        Feature steps = Utils.parse(file, Feature.class);
        assertTrue(
                steps.getSteps()
                        .stream()
                        .allMatch(stepWrapper -> stepWrapper.getName().equals("google"))
        );
        assertEquals(3, steps.getSteps().get(0).getLoop().intValue());
        assertEquals(1, steps.getSteps().get(1).getLoop().intValue());
    }

    @Test
    public void simple() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(SIMPLE);
        Feature feature = Utils.parse(file, Feature.class);
        assertEquals(1, feature.getSteps().size());
        Step step = feature.getSteps().get(0);
        assertEquals("google", step.getName());
        assertEquals(3, step.getLoop().intValue());
        assertEquals("http://www.google.com", step.getUrl());
    }

    @Test
    public void scenario() throws FileNotFoundException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(FEATURE);
        Feature feature = Utils.parse(file, Feature.class);
        assertEquals(2, feature.getScenarios().size());
        assertEquals(3, feature.getSteps().size());
    }
}
