package com.lloyds.runner;

import com.google.common.collect.Table;
import com.lloyds.antlr.lazytester.autogen.LazyTesterLexer;
import com.lloyds.antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.builder.ScenarioListener;
import com.lloyds.model.Scenario;
import com.lloyds.model.ValidatedAssertions;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

public class ScenarioRunnerTest {

    private static int SCENARIO_LOOP = 2;
    private static int STEP_LOOP = 3;

    private static final String SCENARIO_1 =
            "scenarios: [" +
            "  { " +
            "    name: \"scenario 1\"; " +
            "    loop: "+ SCENARIO_LOOP +"; " +
            "    steps: [ " +
            "      {" +
            "        name: \"step 1\"; " +
            "        operation: GET; " +
            "        loop: "+ STEP_LOOP +"; " +
            "        url: \"http://jsonplaceholder.typicode.com\" ; " +
            "      };" +
            "    ];" +
            "  };" +
            "]; ";

    private static final String SCENARIO_2 =
            "scenarios: [" +
            "  { " +
            "    name: \"scenario 1\"; " +
            "    steps: [ " +
            "      {" +
            "        name: \"step 1\"; " +
            "        operation: GET; " +
            "        url: \"http://jsonplaceholder.typicode.com\" ; " +
            "        params: { " +
            "          q: lbg; " +
            "          aq: f;  " +
            "        }; " +
            "      };" +
            "    ]; " +
            "  };" +
            "]; ";

    private static final String SCENARIO_3 =
            "scenarios: [" +
            "  { " +
            "    name: \"scenario 1\"; " +
            "    steps: [ " +
            "      {" +
            "        name: \"step 1\"; " +
            "        operation: GET; " +
            "        url: \"http://jsonplaceholder.typicode.com\" ; " +
            "        assertions: {\n" +
            "           status: 200;\n" +
            "           headers: {\n" +
            "              CF-Cache-Status: HIT;\n" +
            "              Cache-Control: \"public, max-age=14400\";" +
            "           };\n" +
            "        };" +
            "      };" +
            "    ]; " +
            "  };" +
            "]; ";

    private static final String SCENARIO_4 =
            " scenarios: [ \n" +
                    "  { \n" +
                    "    name: \"GET\"; \n" +
                    "    steps: [ \n" +
                    "      {\n" +
                    "        name: \"step 1\"; \n" +
                    "        operation: GET; \n" +
                    "        url: \"https://jsonplaceholder.typicode.com/todos/1\"; \n" +
                    "        assertions: {\n" +
                    "           status: 200;\n" +
                    "           body: { \n " +
                    "              userId: 1; " +
                    "              id: 1; " +
                    "              title: \"delectus aut autem\"; " +
                    "              completed: false; " +
                    "           };\n" +
                    "        };" +
                    "      };\n" +
                    "    ];\n" +
                    "  }; \n" +
                    "];\n";

    @Test
    public void testResults() throws IOException {
        LinkedList<Scenario> scenarios = getScenario(SCENARIO_1);
        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results = scenarioRunner.getResults();
        Scenario scenario = scenarios.get(0);
        Assert.assertEquals(scenario.getLoop().intValue(), results.get(scenario).rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.get(scenario).columnKeySet().size());
    }

    @Test
    public void testGetFullUrlAndParams() throws IOException {
        LinkedList<Scenario> scenarios = getScenario(SCENARIO_2);
        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results = scenarioRunner.getResults();
        Scenario scenario = scenarios.get(0);
        Assert.assertEquals(scenario.getLoop().intValue(), results.get(scenario).rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.get(scenario).columnKeySet().size());
    }

    @Test
    public void testAssertions() throws IOException {
        LinkedList<Scenario> scenarios = getScenario(SCENARIO_3);
        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results = scenarioRunner.getResults();
        Scenario scenario = scenarios.get(0);
        ValidatedAssertions validated = results.get(scenario).row(0).get(0);
        Assert.assertTrue(validated.toString(), validated.isValid());
        Assert.assertEquals(scenario.getLoop().intValue(), results.get(scenario).rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.get(scenario).columnKeySet().size());
    }

    @Test
    public void testAssertionJson() throws IOException {
        LinkedList<Scenario> scenarios = getScenario(SCENARIO_4);
        ScenarioRunner scenarioRunner = new ScenarioRunner(HttpClients.createDefault(), scenarios);
        scenarioRunner.runScenarios();
        Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results = scenarioRunner.getResults();
        Scenario scenario = scenarios.get(0);
        ValidatedAssertions validated = results.get(scenario).row(0).get(0);
        Assert.assertTrue(validated.toString(), validated.isValid());
        Assert.assertEquals(scenario.getLoop().intValue(), results.get(scenario).rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), results.get(scenario).columnKeySet().size());
    }

    private LinkedList<Scenario> getScenario(String scenario) {
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