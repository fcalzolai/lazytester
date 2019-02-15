package antlr.lazytester.autogen;

import com.google.common.collect.ImmutableList;
import com.lloyds.errorListener.ExceptionThrowerErrorListener;
import com.lloyds.model.Scenario;
import com.lloyds.model.ScenarioListener;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LazyTesterLexerTest {

    private static final List<String> INCORRECT_SCENARIOS = ImmutableList.<String>builder()
            .add("import path/to/test " +
                 "{\"name\": \"get google\"}")
            .add("import path/to/test; " +
                 "{" +
                    "\"name\": \"get google\"," +
                 "}")
            .build();

    private static final List<String> CORRECT_SCENARIOS = ImmutableList.<String>builder()
            .add("import test; " +
                 "{\"name\": \"get google\"}")
            .add("import path/to/test; " +
                 "import path/to/test2; "+
                 "{\"name\": \"get google\"}")
            .add("{\"steps\": [{ \"name\": \"get google\"}]} ")
            .add("{ " +
                    "\"steps\": [ " +
                    "   { " +
                    "      \"name\": \"get google\",  " +
                    "      \"url\": \"https://www.google.com\", " +
                    "      \"operation\": \"GET\", " +
                    "        \"params\" : { " +
                    "        \"key1\" : \"value1\", " +
                    "        \"key2\" : \"value2\"  " +
                    "      }" +
                    "  }" +
                    "]" +
                    "} ")
            .build();

    @Test
    public void TestIncorrectScenarios() {
        INCORRECT_SCENARIOS.forEach(s -> {
                    CharStream cs = CharStreams.fromString(s);
                    LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
                    CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokensß
                    LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens
                    parser.addErrorListener(new ExceptionThrowerErrorListener());
                    try {
                        parser.scenario_file();
                        Assert.fail("It should throw a RunTimeException");
                    } catch (RuntimeException e) {

                    }
                }
        );
    }

    @Test
    public void TestCorrectScenarios() {
        CORRECT_SCENARIOS.forEach(s -> {
                    CharStream cs = CharStreams.fromString(s);
                    LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
                    CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
                    LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens
                    ANTLRErrorListener l = new ExceptionThrowerErrorListener();
                    parser.addErrorListener(l);
                    parser.scenario_file();
                }
        );
    }
}