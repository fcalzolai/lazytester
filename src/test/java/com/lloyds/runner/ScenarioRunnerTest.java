package com.lloyds.runner;

import antlr.lazytester.autogen.LazyTesterLexer;
import antlr.lazytester.autogen.LazyTesterParser;
import com.google.common.collect.ImmutableTable;
import com.lloyds.model.Scenario;
import com.lloyds.model.ScenarioListener;
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
    private static final String DEF1 = "{ " +
            "\"name\": \"scenario 1\", " +
            "\"loop\": "+ SCENARIO_LOOP +" , " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"loop\": "+ STEP_LOOP +", " +
            "                \"url\": \"http://www.google.com\", " +
            "                \"assertions\": {" +
            "                    \"status\": 200 " +
            "                  }" +
            "              }" +
            "           ]" +
            "} ";

    //https://www.google.co.uk/search?q=lbg&aq=f
    private static final String DEF2 = "{ " +
            "\"name\": \"scenario 1\", " +
            "\"loop\": "+ SCENARIO_LOOP +" , " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"loop\": "+ STEP_LOOP +", " +
            "                \"url\": \"http://www.google.com\", " +
            "                \"assertions\": {" +
            "                    \"status\": 200 " +
            "                  }" +
            "              }" +
            "           ]" +
            "} ";

    @Test
    public void testResults() throws IOException {
        Scenario scenario = getScenario();
        HttpClient client = HttpClients.createDefault();
        ScenarioRunner scenarioRunner = new ScenarioRunner(client, scenario);
        scenarioRunner.runAll();
        ImmutableTable<Integer, Integer, HttpResponse> results = scenarioRunner.getResults();
        Assert.assertEquals(SCENARIO_LOOP, results.rowKeySet().size());
        Assert.assertEquals(STEP_LOOP, results.columnKeySet().size());
    }

    @Test
    public void testGetFullUrl() throws IOException {
        Scenario scenario = getScenario();
        HttpClient client = HttpClients.createDefault();
        ScenarioRunner scenarioRunner = new ScenarioRunner(client, scenario);
        scenarioRunner.runAll();
        ImmutableTable<Integer, Integer, HttpResponse> results = scenarioRunner.getResults();
        Assert.assertEquals(SCENARIO_LOOP, results.rowKeySet().size());
        Assert.assertEquals(STEP_LOOP, results.columnKeySet().size());
    }

    private Scenario getScenario() {
        CharStream cs = CharStreams.fromString(DEF1);
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