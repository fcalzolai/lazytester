package com.lloyds.antlr.lazytester.autogen;

import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import com.lloyds.utils.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScenarioBuilderFromFilesTest {

    private static final String PATH_1 = "lazy/google.lz";

    @Test
    public void testGoogle() throws IOException {
        LinkedList<Scenario> scenarios = Utils.createScenarioFromResource(PATH_1);
        Assert.assertEquals(2, scenarios.size());

        Scenario scenario = scenarios.get(0);
        Assert.assertNotNull(scenario);
        scenario.getSteps()
                .forEach(step -> Assert.assertTrue("Missing reference to parent", step.getParent().isPresent()));

        scenario = scenarios.get(1);
        Assert.assertNotNull(scenario);
        scenario.getSteps()
                .forEach(step -> Assert.assertTrue("Missing reference to parent", step.getParent().isPresent()));
    }
}