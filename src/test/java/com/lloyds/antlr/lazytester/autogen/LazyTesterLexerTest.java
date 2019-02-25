package com.lloyds.antlr.lazytester.autogen;

import com.google.common.collect.ImmutableList;
import com.lloyds.errorListener.ExceptionThrowerErrorListener;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
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
                    "          \"key1\" : \"value1\", " +
                    "          \"key2\" : \"value2\"  " +
                    "      }" +
                    "  }" +
                    "]" +
                    "} ")
            .add("{ " +
                    "\"steps\": [ " +
                    "   { " +
                    "      \"name\": \"get google\",  " +
                    "      \"url\": \"https://www.google.com\", " +
                    "      \"operation\": \"GET\", " +
                    "      \"params\" : { " +
                    "          \"key1\" : \"value1\", " +
                    "          \"key2\" : \"value2\"  " +
                    "        }, " +
                    "      \"headers\" : { " +
                    "          \"user-agent\" : \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\", " +
                    "          \"accept-encoding\" : \"gzip, deflate, br\" " +
                    "        }," +
                    "       \"body\" : {}, " +
                    "       \"body\" : \"test\", " +
                    "       \"body\" : {\"test\" : null}, " +
                    "       \"body\" : {\"test\" : \"test\"}, " +
                    "       \"body\" : [{\"test\" : \"test\"}], " +
                    "       \"body\" : [" +
                    "                   {\"test\" : \"test\"}, " +
                    "                   {\"test\" : \"test\"}, " +
                    "                   {\"test\" : \"test\"}" +
                    "                  ] " +
                    "  }" +
                    "]" +
                    "} ")
            .add("import test; " +
                    "\"steps\": [ " +
                    "   { " +
                    "      \"name\": \"get google\",  " +
                    "      \"url\": \"https://www.google.com\", " +
                    "      \"operation\": \"GET\", " +
                    "      \"params\" : { " +
                    "          \"key1\" : \"value1\", " +
                    "          \"key2\" : \"value2\"  " +
                    "        }, " +
                    "      \"headers\" : { " +
                    "          \"user-agent\" : \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\", " +
                    "          \"accept-encoding\" : \"gzip, deflate, br\" " +
                    "        }," +
                    "       \"body\" : {} " +
                    "  }" +
                    "]" +
                    "{" +
                    "  \"name\": \"scenario name\"" +
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