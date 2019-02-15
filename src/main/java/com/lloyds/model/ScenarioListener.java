package com.lloyds.model;

import antlr.lazytester.autogen.LazyTesterBaseListener;
import antlr.lazytester.autogen.LazyTesterParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.LinkedList;
import java.util.List;

public class ScenarioListener extends LazyTesterBaseListener {

    private Scenario.ScenarioBuilder scenarioBuilder;
    private Scenario scenario;
    private boolean isInStep;
    private LinkedList<Step> steps;
    private Step.StepBuilder stepBuilder;

    @Override
    public void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        scenarioBuilder = Scenario.getScenarioBuilder();
        isInStep = false;
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
        super.enterParams(ctx);
        List<ParseTree> children = ctx.children;
        String k = "";
        String v = "";
        for (int i = 0; i < children.size(); i++) {
            if(i % 4 == 0) {
                k = children.get(i).getText().replace("\"", "");
            } else if(i % 4 == 2) {
                v = children.get(i).getText().replace("\"", "");
                stepBuilder.putParam(k, v);
            }
        }

    }

    @Override
    public void exitParams(LazyTesterParser.ParamsContext ctx) {
        super.exitParams(ctx);
    }

    public Scenario getScenario() {
        return scenario;
    }
}
