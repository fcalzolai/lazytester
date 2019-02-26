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
import java.util.List;
import java.util.Map;

public class ScenarioBuilderTest {

    private static final String HEADERS_USER_AGENT = "\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 " +
                                                     "(KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\"";

    private static final String BODY =  "{ " +
//                                        "  \"body_key_1\" : \"body_key_val_1\", " +
//                                        "  \"body_key_2\" : \"body_key_val_2\" " +
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
            "        body : "+BODY+" ;\n" +
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
            "    body : "+BODY+" ;\n" +
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
            "    body : "+BODY+" ;\n" +
            "  }; " +
            "  { " +
            "    name: \"step 33\"; " +
            "    operation: POST; " +
            "    url: \"http://www.lbg2.com\"; " +
            "    params: { " +
            "      lbg2: val; " +
            "    }; " +
            "    body : "+BODY+" ;\n" +
            "  }; " +
            "] ;";

    @Test
    public void test1(){
        System.out.println(Utils.parseScenario(SCENARIO_1));
    }

    @Test
    public void test2() throws IOException {
        Scenario scenario = Utils.parseScenario(SCENARIO_1);

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
        Scenario scenario = Utils.parseScenario(SCENARIO_2);
        Map<String, String> params = scenario.getSteps().get(0).getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));
    }

    @Test
    public void testHeadersCreation(){
        Scenario scenario = Utils.parseScenario(SCENARIO_2);
        Map<String, String> headers = scenario.getSteps().get(0).getHeaders();
        Assert.assertEquals(2, headers.size());
        Assert.assertEquals(HEADERS_USER_AGENT, headers.get("user-agent"));
        Assert.assertEquals("\"gzip, deflate, br\"", headers.get("accept-encoding"));
    }

    @Test
    public void testBodyCreation(){
        Scenario scenario = Utils.parseScenario(SCENARIO_2);
        String body = scenario.getSteps().get(0).getBody();
        Assert.assertEquals(BODY.replaceAll(" ",""), body);
    }

    @Test
    public void testTwoStepsCreation(){
        Scenario scenario = Utils.parseScenario(SCENARIO_3);
        List<Step> steps = scenario.getSteps();
        Assert.assertEquals(2, steps.size());

        Step step;
        step = steps.get(0);
        Assert.assertEquals("step 31", step.getName());
        Assert.assertEquals("GET", step.getOperation());
        Assert.assertEquals("http://www.google.com", step.getUrl());
        Map<String, String> params = step.getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));

        step = steps.get(1);
        Assert.assertEquals("step 32", step.getName());
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg"));

        Assert.assertEquals(BODY.replaceAll(" ",""), step.getBody());
    }

    @Test
    public void testThreeStepsCreation(){
        Scenario scenario = Utils.parseScenario(SCENARIO_4);
        List<Step> steps = scenario.getSteps();
        Assert.assertEquals(3, steps.size());

        Step step;
        step = steps.get(0);
        Assert.assertEquals("step 31", step.getName());
        Assert.assertEquals("GET", step.getOperation());
        Assert.assertEquals("http://www.google.com", step.getUrl());
        Map<String, String> params = step.getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));

        step = steps.get(1);
        Assert.assertEquals("step 32", step.getName());
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg"));

        step = steps.get(2);
        Assert.assertEquals("step 33", step.getName());
        Assert.assertEquals("POST", step.getOperation());
        Assert.assertEquals("http://www.lbg2.com", step.getUrl());
        params = step.getParams();
        Assert.assertEquals(1, params.size());
        Assert.assertEquals("val", params.get("lbg2"));

        Assert.assertEquals(BODY.replaceAll(" ",""), step.getBody());
    }

    private void runHttpGet(HttpClient client, HttpUriRequest http) throws IOException {
        HttpResponse response = client.execute(http);
        System.out.println(response);
        HttpEntity entity1 = response.getEntity();
        EntityUtils.consume(entity1);
    }

}