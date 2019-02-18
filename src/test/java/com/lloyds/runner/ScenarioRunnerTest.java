package com.lloyds.runner;

import antlr.lazytester.autogen.LazyTesterLexer;
import antlr.lazytester.autogen.LazyTesterParser;
import com.google.common.collect.ImmutableTable;
import com.lloyds.model.Scenario;
import com.lloyds.builder.ScenarioListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ScenarioRunnerTest {

    private static int SCENARIO_LOOP = 2;
    private static int STEP_LOOP = 3;
    private static final String SCENARIO_1 = "{ " +
            "\"name\": \"scenario 1\", " +
            "\"loop\": "+ SCENARIO_LOOP +" , " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"loop\": "+ STEP_LOOP +", " +
            "                \"url\": \"http://www.google.com\" " +
            "              }" +
            "           ]" +
            "} ";

    private static final String SCENARIO_2 = "{ " +
            "\"name\": \"scenario 1\", " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"url\": \"http://www.google.com\", " +
            "                \"params\": { " +
            "                     \"q\": \"lbg\", " +
            "                     \"aq\": \"f\"  " +
            "                  }" +
            "              }" +
            "           ]" +
            "} ";

    @Test
    public void testResults() throws IOException {
        Scenario scenario = getScenario(SCENARIO_1);
        HttpClient client = HttpClients.createDefault();
        ScenarioRunner scenarioRunner = new ScenarioRunner(client, scenario);
        scenarioRunner.runAll();
        ImmutableTable<Integer, Integer, HttpResponse> results = scenarioRunner.getResults();
        Assert.assertEquals(scenario.getLoop().intValue(), results.rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.columnKeySet().size());
    }

    @Test
    public void testGetFullUrl() throws IOException {
        Scenario scenario = getScenario(SCENARIO_1);
        HttpClient client = HttpClients.createDefault();
        ScenarioRunner scenarioRunner = new ScenarioRunner(client, scenario);
        scenarioRunner.runAll();
        ImmutableTable<Integer, Integer, HttpResponse> results = scenarioRunner.getResults();
        Assert.assertEquals(scenario.getLoop().intValue(), results.rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.columnKeySet().size());
    }

    @Test
    public void testGetFullUrlAndParams() throws IOException {
        Scenario scenario = getScenario(SCENARIO_2);
        HttpClient client = HttpClients.createDefault();
        ScenarioRunner scenarioRunner = new ScenarioRunner(client, scenario);
        scenarioRunner.runAll();
        ImmutableTable<Integer, Integer, HttpResponse> results = scenarioRunner.getResults();
        Assert.assertEquals(scenario.getLoop().intValue(), results.rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.columnKeySet().size());
    }

    private Scenario getScenario(String scenario) {
        CharStream cs = CharStreams.fromString(scenario);
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        return listener.getScenario();
    }
}