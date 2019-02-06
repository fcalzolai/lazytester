package antlr.lazytester.buider;

import antlr.lazytester.autogen.LazyTesterLexer;
import antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.model.Scenario;
import com.lloyds.model.ScenarioListener;
import com.lloyds.model.Step;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

public class ScenarioBuilderTest {

    private static final String DEF1 = "{ " +
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

    @Test
    public void test1(){
        CharStream cs = CharStreams.fromString(DEF1);
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
        CharStream cs = CharStreams.fromString(DEF1);
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
        HttpUriRequest http = step.createHttpRequest();

        System.out.println(client.execute(http));
        System.out.println(client.execute(http));
    }

}