package com.lloyds.utils;

import com.lloyds.antlr.lazytester.autogen.LazyTesterLexer;
import com.lloyds.antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.builder.ScenarioListener;
import com.lloyds.model.Scenario;
import com.lloyds.runner.ScenarioRunner;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static List<ScenarioRunner> createScenarioRunner(String str){
        HttpClient httpClient = HttpClients.createDefault();
        List<Scenario> scenario = createScenario(str);
        return createScenarioRunner(httpClient, scenario);
    }

    public static List<Scenario> createScenario(String str){
        CharStream cs = CharStreams.fromString(str);
        return createScenarios(cs);
    }

    public static List<Scenario> createScenarioFromResource(String fileName) throws IOException {
        InputStream is = Utils.class.getClassLoader().getResourceAsStream(fileName);
        CharStream cs = CharStreams.fromStream(is);
        return createScenarios(cs);
    }

    public static List<ScenarioRunner> createScenarioRunnerFromResource(String fileName) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        InputStream is = Utils.class.getClassLoader().getResourceAsStream(fileName);
        CharStream cs = CharStreams.fromStream(is);
        List<Scenario> scenarios = createScenarios(cs);
        return createScenarioRunner(httpClient, scenarios);
    }

    private static List<ScenarioRunner> createScenarioRunner(HttpClient httpClient, List<Scenario> scenarios) {
        return scenarios.stream()
                .map(s -> new ScenarioRunner(httpClient, s))
                .collect(Collectors.toList());
    }

    public static List<Scenario> createScenarioFromFileName(String fileName) throws IOException {
        CharStream cs = CharStreams.fromFileName(fileName);
        return createScenarios(cs);
    }

    public static ScenarioListener createScenarioListener(String str){
        CharStream cs = CharStreams.fromString(str);
        return getScenarioListener(cs);
    }

    public static ScenarioListener createScenarioListerFromResource(String fileName) throws IOException {
        InputStream is = Utils.class.getClassLoader().getResourceAsStream(fileName);
        CharStream cs = CharStreams.fromStream(is);
        return getScenarioListener(cs);
    }

    public static ScenarioListener createScenarioListerFromFileName(String fileName) throws IOException {
        CharStream cs = CharStreams.fromFileName(fileName);
        return getScenarioListener(cs);
    }

    private static List<Scenario> createScenarios(CharStream cs) {
        ScenarioListener listener = getScenarioListener(cs);
        return listener.getScenario();
    }

    private static ScenarioListener getScenarioListener(CharStream cs) {
        LazyTesterLexer lexer = new LazyTesterLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        LazyTesterParser parser = new LazyTesterParser(tokens);  //parse the tokens

        ParseTree tree = parser.scenario_file();
        ScenarioListener listener = new ScenarioListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
        return listener;
    }
}
