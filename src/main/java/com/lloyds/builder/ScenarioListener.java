package com.lloyds.builder;

import antlr.lazytester.autogen.LazyTesterBaseListener;
import antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.model.Scenario;
import com.lloyds.model.Step;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class ScenarioListener extends LazyTesterBaseListener {

    private Scenario.ScenarioBuilder scenarioBuilder;
    private Scenario scenario;
    private boolean isInStep;
    private LinkedList<Step> steps;
    private Step.StepBuilder stepBuilder;
    private MapDef map;

    @Override
    public void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        scenarioBuilder = Scenario.getScenarioBuilder();
        isInStep = false;
        map = MapDef.HEADERS;
    }

    @Override
    public void exitScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        scenario = scenarioBuilder.build();
    }

    @Override
    public void enterScenario_name(LazyTesterParser.Scenario_nameContext ctx) {
        scenarioBuilder.setName(ctx.getChild(2).getText());
    }

    @Override
    public void exitScenario_name(LazyTesterParser.Scenario_nameContext ctx) {
        super.exitScenario_name(ctx);
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
    public void enterSteps_def(LazyTesterParser.Steps_defContext ctx) {
        isInStep = true;
        steps = new LinkedList<>();
    }

    @Override
    public void exitStep_def(LazyTesterParser.Step_defContext ctx) {
        isInStep = false;
        scenarioBuilder.setSteps(steps);
    }

    @Override
    public void enterSteps(LazyTesterParser.StepsContext ctx) {
        stepBuilder = Step.getStepBuilder();
    }

    @Override
    public void exitSteps_def(LazyTesterParser.Steps_defContext ctx) {
        Step step = stepBuilder.build();
        steps.add(step);
        stepBuilder = Step.getStepBuilder();
    }

    @Override
    public void enterStep_name(LazyTesterParser.Step_nameContext ctx) {
        stepBuilder.setName(ctx.getChild(2).getText());
    }

    private static String getEscapedChildText(ParserRuleContext ctx){
        return ctx.getChild(2).getText().replace("\"", "");
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
                k = children.get(i).getText().replace("\"", "");
            } else if(i % 4 == 2) {
                v = children.get(i).getText().replace("\"", "");
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
    public void enterBody_def(LazyTesterParser.Body_defContext ctx) {
        String body = ctx.getChild(2).getText();
        stepBuilder.setBody(body);
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

    public Scenario getScenario() {
        return scenario;
    }
}
