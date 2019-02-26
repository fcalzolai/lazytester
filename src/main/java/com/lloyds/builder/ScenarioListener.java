package com.lloyds.builder;

import com.google.common.collect.ImmutableMap;
import com.lloyds.antlr.lazytester.autogen.LazyTesterBaseListener;
import com.lloyds.antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;
import java.util.function.BiConsumer;

public class ScenarioListener extends LazyTesterBaseListener {

    private static final String IMPORT = "import";

    private Scenario.ScenarioBuilder scenarioBuilder;
    private LinkedList<Scenario> scenarios;
    private boolean isInStep;
    private LinkedList<Step> steps;
    private Map<String, Step> stepsMap;
    private Step.StepBuilder stepBuilder;
    private MapDef map;

    public LinkedList<Scenario> getScenario() {
        return scenarios;
    }

    public ImmutableMap<String, Step> getAllSteps(){
        return ImmutableMap.copyOf(stepsMap);
    }

    @Override
    public void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        isInStep = false;
        map = MapDef.HEADERS;
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
    public void enterAssertions(LazyTesterParser.AssertionsContext ctx) {
        stepBuilder.setAssertions(getEscapedChildText(ctx));
    }

    @Override
    public void enterParams(LazyTesterParser.ParamsContext ctx) {
        List<ParseTree> children = ctx.children;
        BiConsumer<String, String> put = getPutParamConsumer();
        String k = "";
        String v = "";
        for (int i = 0; i < children.size(); i++) {
            if(i % 4 == 0) {
                k = children.get(i).getText();
            } else if(i % 4 == 2) {
                v = children.get(i).getText();
                put.accept(k, v);
            }
        }

    }

    @Override
    public void enterParams_def(LazyTesterParser.Params_defContext ctx) {
        map = MapDef.QUERY_PARAMS;
    }

    @Override
    public void exitParams_def(LazyTesterParser.Params_defContext ctx) {
        map = MapDef.NULL;
    }

    @Override
    public void enterHeaders_def(LazyTesterParser.Headers_defContext ctx) {
        map = MapDef.HEADERS;
    }

    @Override
    public void exitHeaders_def(LazyTesterParser.Headers_defContext ctx) {
        map = MapDef.NULL;
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
            String toImport = terminal
                    .substring(IMPORT.length(), terminal.lastIndexOf(';'))
                    .trim();

//            try {
//                Utils.createScenarioFromFileName(toImport);
//            } catch (IOException e) {
//                throw new IllegalArgumentException("Exception while parsing file: "+toImport);
//            }
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
            case NULL:
                throw new IllegalArgumentException("Map has been set to NULL");
            default:
                throw new IllegalArgumentException("Unknown map: unable to perform put.");
        }
    }
}
