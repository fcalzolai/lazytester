package com.lloyds.antlr.lazytester.autogen;

import com.google.common.collect.ImmutableMap;
import com.lloyds.builder.ScenarioListener;
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
import java.util.Map;

public class ScenarioBuilderTest {

    private static final String HEADERS_USER_AGENT = "\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 " +
                                                     "(KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\"";

    private static final String EMPTY_BODY =  "{ }";

    private static final String BODY =  "{ " +
                                        " \"test\": true," +
                                        " \"test2\": \"val2\"" +
                                        "}";

    private static final String BODY_ASSERTION = "{ " +
                                                  " test: true;" +
                                                  " test2: val2;" +
                                                  "}";

    private static final String SCENARIO_1 = "import test; " +
            " scenarios: [" +
            "  {  " +
            "    name: \"scenario 1\"; " +
            "    loop: 2; " +
            "    steps: [ " +
            "      {" +
            "        name: \"step 1\"; " +
            "        operation: GET; " +
            "        loop: 3; " +
            "        url: \"http://www.google.com\"; " +
            "        assertions: {" +
            "          status:200;" +
            "        }; " +
            "      };" +
            "    ];" +
            "  }; " +
            "];";

    private static final String SCENARIO_2 =
            " scenarios: [ \n" +
            "  { \n" +
            "    name: \"scenario 2\"; \n" +
            "    steps: [ \n" +
            "      {\n" +
            "        name: \"step 1\"; \n" +
            "        operation: GET; \n" +
            "        url: \"http://www.google.com\"; \n" +
            "        params: {\n" +
            "          q: lbg; \n" +
            "          aq: f;  \n" +
            "        }; \n" +
            "        headers : { \n" +
            "          user-agent : "+HEADERS_USER_AGENT+"; \n" +
            "          accept-encoding : \"gzip, deflate, br\"; \n" +
            "         }; \n" +
            "        body : "+ EMPTY_BODY +" ;\n" +
            "      };\n" +
            "    ];\n" +
            "  }; \n" +
            "];\n";

    private static final String SCENARIO_3 =
            "steps: [ " +
            "  { " +
            "    name: \"step 31\"; " +
            "    operation: GET; " +
            "    url: \"http://www.google.com\"; " +
            "    params: { " +
            "      q: lbg; " +
            "      aq: f; " +
            "    }; " +
            "  }; " +
            "  { " +
            "    name: \"step 32\"; " +
            "    operation: POST; " +
            "    url: \"http://www.lbg.com\"; " +
            "    params: { " +
            "      lbg: val; " +
            "    }; " +
            "    body : "+ EMPTY_BODY +" ;\n" +
            "  }; " +
            "] ;";

    private static final String SCENARIO_4 =
            "steps: [ " +
            "  { " +
            "    name: \"step 31\"; " +
            "    operation: GET; " +
            "    url: \"http://www.google.com\"; " +
            "    params: { " +
            "      q: lbg; " +
            "      aq: f; " +
            "    }; " +
            "  }; " +
            "  { " +
            "    name: \"step 32\"; " +
            "    operation: POST; " +
            "    url: \"http://www.lbg.com\"; " +
            "    params: { " +
            "      lbg: val; " +
            "    }; " +
            "    body : "+ BODY +" ;\n" +
            "  }; " +
            "  { " +
            "    name: \"step 33\"; " +
            "    operation: POST; " +
            "    url: \"http://www.lbg2.com\"; " +
            "    params: { " +
            "      lbg2: val; " +
            "    }; " +
            "    body : "+ EMPTY_BODY +" ;\n" +
            "    assertions: { " +
            "      status:200; " +
            "      body:"+ BODY_ASSERTION +"; " +
            "      headers: {\n" +
            "        accept-encoding: \"gzip, deflate, br\";\n" +
            "      };" +
            "    }; " +
            "  }; " +
            "] ;";

    @Test
    public void test1(){
        System.out.println(Utils.createScenario(SCENARIO_1));
    }

    @Test
    public void test2() throws IOException {
        Scenario scenario = Utils.createScenario(SCENARIO_1).get(0);

        Step step = scenario.getSteps().get(0);
        HttpClient client = HttpClients.createDefault();
        HttpUriRequest http = step.getHttpRequest();

        runHttpGet(client, http);
        runHttpGet(client, http);
        runHttpGet(client, http);
        runHttpGet(client, http);
    }

    @Test
    public void testParamsCreation(){
        Scenario scenario = Utils.createScenario(SCENARIO_2).get(0);
        Map<String, String> params = scenario.getSteps().get(0).getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));
    }

    @Test
    public void testHeadersCreation(){
        Scenario scenario = Utils.createScenario(SCENARIO_2).get(0);
        Map<String, String> headers = scenario.getSteps().get(0).getHeaders();
        Assert.assertEquals(2, headers.size());
        Assert.assertEquals(HEADERS_USER_AGENT, headers.get("user-agent"));
        Assert.assertEquals("\"gzip, deflate, br\"", headers.get("accept-encoding"));
    }

    @Test
    public void testBodyCreation(){
        Scenario scenario = Utils.createScenario(SCENARIO_2).get(0);
        String body = scenario.getSteps().get(0).getBody();
        Assert.assertEquals(EMPTY_BODY.replaceAll(" ",""), body);
    }

    @Test
    public void testTwoStepsCreation(){
        ScenarioListener scenarioListener = Utils.createScenarioListener(SCENARIO_3);
        ImmutableMap<String, Step> steps = scenarioListener.getAllSteps();
        Assert.assertEquals(2, steps.size());

        Step step;
        step = steps.get("step 31");
        Assert.assertEquals("GET", step.getOperation());
        Assert.assertEquals("http://www.google.com", step.getUrl());
        Map<String, String> params = step.getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));

        step = steps.get("step 32");
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg"));

        Assert.assertEquals(EMPTY_BODY.replaceAll(" ",""), step.getBody());
    }

    @Test
    public void testThreeStepsCreation(){
        ScenarioListener scenarioListener = Utils.createScenarioListener(SCENARIO_4);
        ImmutableMap<String, Step> steps = scenarioListener.getAllSteps();
        Assert.assertEquals(3, steps.size());

        Step step;
        step = steps.get("step 31");
        Assert.assertEquals("GET", step.getOperation());
        Assert.assertEquals("http://www.google.com", step.getUrl());
        Map<String, String> params = step.getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));
        Assert.assertFalse(step.getAssertions().getStatus().isPresent());
        Assert.assertEquals(0, step.getAssertions().getBody().size());
        Assert.assertEquals(0, step.getAssertions().getHeaders().size());

        step = steps.get("step 32");
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg"));
        Assert.assertFalse(step.getAssertions().getStatus().isPresent());
        Assert.assertEquals(0, step.getAssertions().getBody().size());
        Assert.assertEquals(0, step.getAssertions().getHeaders().size());

        step = steps.get("step 33");
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg2.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg2"));
        Assert.assertEquals(Integer.valueOf(200), step.getAssertions().getStatus().get());
        Map<String, String> body = step.getAssertions().getBody();
        Assert.assertEquals(2, body.size());
        Assert.assertEquals("true", body.get("test"));
        Assert.assertEquals("val2", body.get("test2"));


        Map<String, String> header = step.getAssertions().getHeaders();
        Assert.assertEquals(1, header.size());
        Assert.assertEquals("gzip, deflate, br", header.get("accept-encoding"));


        Assert.assertEquals(EMPTY_BODY.replaceAll(" ",""), step.getBody());
    }

    private void runHttpGet(HttpClient client, HttpUriRequest http) throws IOException {
        HttpResponse response = client.execute(http);
        System.out.println(response);
        HttpEntity entity1 = response.getEntity();
        EntityUtils.consume(entity1);
    }

}