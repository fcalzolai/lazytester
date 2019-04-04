package com.lloyds.builder;

import com.google.common.collect.ImmutableMap;
import com.lloyds.antlr.lazytester.autogen.LazyTesterBaseListener;
import com.lloyds.antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.model.Assertions;
import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import com.lloyds.utils.Utils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.String.format;

public class ScenarioListener extends LazyTesterBaseListener {

    private static final String IMPORT = "import";

    private Scenario.ScenarioBuilder scenarioBuilder;
    private LinkedList<Scenario> scenarios;
    private boolean isInStep;
    private LinkedList<Step> steps;
    private Map<String, Step> stepsMap;
    private Step.StepBuilder stepBuilder;
    private ParamsDef map;
    private Assertions.AssertionBuilder assertionBuilder;

    public LinkedList<Scenario> getScenario() {
        return scenarios;
    }

    public ImmutableMap<String, Step> getAllSteps(){
        return ImmutableMap.copyOf(stepsMap);
    }

    @Override
    public void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        isInStep = false;
        map = ParamsDef.HEADERS;
        stepsMap = new HashMap<>();
        scenarios = new LinkedList<>();
    }

    @Override
    public void enterScenario_def(LazyTesterParser.Scenario_defContext ctx) {
        scenarioBuilder = Scenario.getScenarioBuilder();
    }

    @Override
    public void exitScenario_def(LazyTesterParser.Scenario_defContext ctx) {
        scenarios.add(scenarioBuilder.build());
        scenarioBuilder = null;
    }

    @Override
    public void enterSteps_section(LazyTesterParser.Steps_sectionContext ctx) {
        steps = new LinkedList<>();
    }

    @Override
    public void exitSteps_section(LazyTesterParser.Steps_sectionContext ctx) {
        if(scenarioBuilder != null) {
            scenarioBuilder.setSteps(steps);
        }
        steps = null;
    }

    @Override
    public void enterStep_def(LazyTesterParser.Step_defContext ctx) {
        isInStep = true;
        stepBuilder = Step.getStepBuilder();
    }

    @Override
    public void exitStep_def(LazyTesterParser.Step_defContext ctx) {
        isInStep = false;
        Step step = stepBuilder.build();
        steps.add(step);
        stepsMap.put(step.getName(), step);

        stepBuilder = null;
    }

    @Override
    public void enterScenario_name(LazyTesterParser.Scenario_nameContext ctx) {
        scenarioBuilder.setName(getEscapedChildText(ctx));
    }

    @Override
    public void enterLoop_def(LazyTesterParser.Loop_defContext ctx) {
        int loop = Integer.valueOf(ctx.getChild(2).getText());
        if (isInStep) {
            stepBuilder.setLoop(loop);
        } else {
            scenarioBuilder.setLoop(loop);
        }
    }

    @Override
    public void enterStep_name(LazyTesterParser.Step_nameContext ctx) {
        stepBuilder.setName(getEscapedChildText(ctx));
    }

    @Override
    public void enterOperation(LazyTesterParser.OperationContext ctx) {
        stepBuilder.setOperation(getEscapedChildText(ctx));
    }

    @Override
    public void enterUrl_def(LazyTesterParser.Url_defContext ctx) {
        stepBuilder.setUrl(getEscapedChildText(ctx));
    }

    @Override
    public void enterAssertion(LazyTesterParser.AssertionContext ctx) {
        String child = ctx.getChild(0).getText();
        if(child.equals("status")) {
            assertionBuilder.setStatus(Integer.valueOf(ctx.getChild(2).toString()));
        } else if(child.startsWith("headers")) {
            map = ParamsDef.ASSERTIONS_HEADERS;
        } else if(child.startsWith("body")) {
            map = ParamsDef.ASSERTIONS_BODY;
        } else {
            throw new IllegalArgumentException("Unknown Assertion value: " + child);
        }
    }

    @Override
    public void enterAssertions_def(LazyTesterParser.Assertions_defContext ctx) {
        assertionBuilder = Assertions.getBuilder();
    }

    @Override
    public void exitAssertions_def(LazyTesterParser.Assertions_defContext ctx) {
        stepBuilder.setAssertions(assertionBuilder.build());
        assertionBuilder = null;
        map = ParamsDef.NULL;
    }

    @Override
    public void enterParams(LazyTesterParser.ParamsContext ctx) {
        List<ParseTree> children = ctx.children;
        if(children == null)
            return;

        String k = "";
        String v = "";
        for (int i = 0; i < children.size(); i++) {
            if(i % 4 == 0) {
                k = children.get(i).getText();
            } else if(i % 4 == 2) {
                v = children.get(i).getText();
                BiConsumer<String, String> put = getPutParamConsumer();
                put.accept(k, v);
            }
        }

    }

    @Override
    public void enterParams_def(LazyTesterParser.Params_defContext ctx) {
        map = ParamsDef.QUERY_PARAMS;
    }

    @Override
    public void exitParams_def(LazyTesterParser.Params_defContext ctx) {
        map = ParamsDef.NULL;
    }

    @Override
    public void enterHeaders_def(LazyTesterParser.Headers_defContext ctx) {
        if(map != ParamsDef.ASSERTIONS_HEADERS) {
            map = ParamsDef.HEADERS;
        }
    }

    @Override
    public void exitHeaders_def(LazyTesterParser.Headers_defContext ctx) {
        if(map != ParamsDef.ASSERTIONS_HEADERS) {
            map = ParamsDef.NULL;
        }
    }

    @Override
    public void enterExtend_def(LazyTesterParser.Extend_defContext ctx) {
        String parentString = getEscapedChildText(ctx);
        Step parent = this.stepsMap.get(parentString);
        if(parent == null) {
            throw new IllegalArgumentException("Step "+parentString+" has not been declared.");
        }
        stepBuilder.setParent(parent);
    }

    @Override
    public void enterBody_def(LazyTesterParser.Body_defContext ctx) {
        String body = ctx.getChild(2).getText();
        stepBuilder.setBody(body);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        String terminal = node.toString();

        if(terminal.startsWith(IMPORT)) {

            //TODO Implements import correctly
            String toImport = terminal
                    .substring(IMPORT.length(), terminal.lastIndexOf(';'))
                    .trim();

            try {
                ScenarioListener scenarioListener = Utils.createScenarioListerFromResource(toImport);
                ImmutableMap<String, Step> importedSteps = scenarioListener.getAllSteps();
                for (Map.Entry<String, Step> entry: importedSteps.entrySet()) {
                    if(stepsMap.containsKey(entry.getKey())){
                        throw new IllegalArgumentException(format("Step[%s] already defined.", entry));
                    }
                    stepsMap.put(entry.getKey(), entry.getValue());
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(format("Exception while parsing file [%s]", toImport), e);
            }
        }
    }

    private static String getEscapedChildText(ParserRuleContext ctx){
        return ctx.getChild(2).getText().replace("\"", "");
    }

    private BiConsumer<String, String> getPutParamConsumer() {
        switch (map) {
            case HEADERS:
                return (k, v) -> stepBuilder.putHeaders(k, v);
            case QUERY_PARAMS:
                return (k, v) -> stepBuilder.putParam(k, v);
            case ASSERTIONS_HEADERS:
                return (k, v) -> assertionBuilder.putHeader(k, v);
            case ASSERTIONS_BODY:
                return (k, v) -> assertionBuilder.putBody(k, v);
            case NULL:
                throw new IllegalArgumentException("Map has been set to NULL");
            default:
                throw new IllegalArgumentException("Unknown map: unable to perform putHeader.");
        }
    }
}
