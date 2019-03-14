package com.lloyds.runner;

import com.google.common.collect.ImmutableTable;
import com.lloyds.model.Scenario;
import com.lloyds.model.ValidatedAssertions;
import com.lloyds.utils.Utils;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

public class ScenarioRunnerFromFileTest {

    private static final String PATH_1 = "lazy/googleToRun.lz";

//    @Ignore
    @Test
    public void testGoogle() throws IOException {
        LinkedList<Scenario> scenarios = Utils.createScenarioFromResource(PATH_1);
        Scenario scenario = scenarios.get(0);

        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenario);
        scenarioRunner.runScenario();
        ImmutableTable<Integer, Integer, ValidatedAssertions> results = scenarioRunner.getResults();
        results.cellSet().forEach(cell -> {
            System.out.println(cell);
            Assert.assertTrue(cell.getValue().isValid());
        });
    }

}