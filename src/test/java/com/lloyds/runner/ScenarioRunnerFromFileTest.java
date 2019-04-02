package com.lloyds.runner;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.lloyds.model.Scenario;
import com.lloyds.model.ValidatedAssertions;
import com.lloyds.utils.Utils;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

public class ScenarioRunnerFromFileTest {

    private static final String PATH = "lazy/jsonPlaceHolder.lz";

    @Test
    public void testJsonPlaceHolder() throws IOException {
        LinkedList<Scenario> scenarios = Utils.createScenarioFromResource(PATH);

        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results = scenarioRunner.getResults();
        results.entrySet().forEach(e ->
                e.getValue().cellSet().forEach(cell -> {
                    System.out.println(e.getKey() + " --> " + cell);
                    Assert.assertTrue(cell.getValue().isValid());
                }));
    }

}