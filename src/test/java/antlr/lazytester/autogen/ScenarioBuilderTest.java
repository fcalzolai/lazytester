package antlr.lazytester.autogen;

import com.lloyds.model.Scenario;
import com.lloyds.builder.ScenarioListener;
import com.lloyds.model.Step;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScenarioBuilderTest {

    private static final String HEADERS_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";

    private static final String SCENARIO_1 = "import te; { " +
            "\"name\": \"scenario 1\", " +
            "\"loop\": 2 , " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"loop\": 3, " +
            "                \"url\": \"http://www.google.com\", " +
            "                \"assertions\": {" +
            "                    \"status\": 200    " +
            "                  }" +
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
            "                  }," +
            "                \"headers\" : { " +
            "                     \"user-agent\" : \""+HEADERS_USER_AGENT+"\", " +
            "                     \"accept-encoding\" : \"gzip, deflate, br\" " +
            "                } " +
            "              }" +
            "           ]" +
            "} ";


    @Test
    public void test1(){
        CharStream cs = CharStreams.fromString(SCENARIO_1);
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        Scenario scenario = listener.getScenario();
        System.out.println(scenario);
    }

    @Test
    public void test2() throws IOException {
        CharStream cs = CharStreams.fromString(SCENARIO_1);
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        Scenario scenario = listener.getScenario();

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
        CharStream cs = CharStreams.fromString(SCENARIO_2);
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        Scenario scenario = listener.getScenario();
        Map<String, String> params = scenario.getSteps().get(0).getParams();
        Assert.assertEquals(2, params.size());
        Assert.assertEquals("lbg", params.get("q"));
        Assert.assertEquals("f", params.get("aq"));
    }

    @Test
    public void testHeadersCreation(){
        CharStream cs = CharStreams.fromString(SCENARIO_2);
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        Scenario scenario = listener.getScenario();
        Map<String, String> headers = scenario.getSteps().get(0).getHeaders();
        Assert.assertEquals(2, headers.size());
        Assert.assertEquals(HEADERS_USER_AGENT, headers.get("user-agent"));
        Assert.assertEquals("gzip, deflate, br", headers.get("accept-encoding"));
    }

    private void runHttpGet(HttpClient client, HttpUriRequest http) throws IOException {
        HttpResponse response = client.execute(http);
        System.out.println(response);
        HttpEntity entity1 = response.getEntity();
        EntityUtils.consume(entity1);
    }

}