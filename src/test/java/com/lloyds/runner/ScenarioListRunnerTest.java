package com.lloyds.runner;

import com.lloyds.model.Scenario;
import com.lloyds.model.ValidatedAssertions;
import com.lloyds.utils.Utils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ScenarioListRunnerTest {

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
        List<ScenarioRunner> scenarios = Utils.createScenarioRunner(SCENARIO_1);
        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        ScenarioRunner scenarioRunner = scenarios.get(0);
        Scenario scenario = scenarioRunner.getScenario();
        Assert.assertEquals(scenario.getLoop().intValue(), scenarioRunner.getResults().rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), scenarioRunner.getResults().columnKeySet().size());
    }

    @Test
    public void testGetFullUrlAndParams() throws IOException {
        List<ScenarioRunner> scenarios = Utils.createScenarioRunner(SCENARIO_2);
        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        ScenarioRunner scenarioRunner = scenarios.get(0);
        Scenario scenario = scenarioRunner.getScenario();
        Assert.assertEquals(scenario.getLoop().intValue(), scenarioRunner.getResults().rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), scenarioRunner.getResults().columnKeySet().size());
    }

    @Test
    public void testAssertions() throws IOException {
        List<ScenarioRunner> scenarios = Utils.createScenarioRunner(SCENARIO_3);
        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        ScenarioRunner scenarioRunner = scenarios.get(0);
        Scenario scenario = scenarioRunner.getScenario();
        ValidatedAssertions validated = scenarioRunner.getResults().row(0).get(0);
        Assert.assertTrue(validated.toString(), validated.isValid());
        Assert.assertEquals(scenario.getLoop().intValue(), scenarioRunner.getResults().rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), scenarioRunner.getResults().columnKeySet().size());
    }

    @Test
    public void testAssertionJson() throws IOException {
        List<ScenarioRunner> scenarios = Utils.createScenarioRunner(SCENARIO_4);
        ScenarioListRunner scenarioListRunner = new ScenarioListRunner(scenarios);
        scenarioListRunner.runScenarios();
        ScenarioRunner scenarioRunner = scenarios.get(0);
        Scenario scenario = scenarioRunner.getScenario();
        ValidatedAssertions validated = scenarioRunner.getResults().row(0).get(0);
        Assert.assertTrue(validated.toString(), validated.isValid());
        Assert.assertEquals(scenario.getLoop().intValue(), scenarioRunner.getResults().rowKeySet().size());
        Assert.assertEquals(scenario.getSteps().get(0).getLoop().intValue(), scenarioRunner.getResults().columnKeySet().size());
    }
}