package antlr.lazytester.buider;

import antlr.lazytester.autogen.LazyTesterLexer;
import antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.composite.Scenario;
import com.lloyds.composite.ScenarioListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class LazyTesterBuilderTest {

    private static final String DEF1 = "{ " +
            "\"name\": \"scenario 1\", " +
            "\"loop\": 13 , " +
            "\"steps\": [ {" +
            "                \"name\": \"step 1\" , " +
            "                \"operation\": \"GET\", " +
            "                \"loop\": 13, " +
            "                \"url\": \"www.google.com\", " +
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

}