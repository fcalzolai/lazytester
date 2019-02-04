package com.lloyds.composite;

import antlr.lazytester.autogen.LazyTesterBaseListener;
import antlr.lazytester.autogen.LazyTesterParser;

import java.util.LinkedList;

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
        if (isInStep){
            //TODO
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
    public void enterStep_def(LazyTesterParser.Step_defContext ctx) {
        stepBuilder = Step.getStepBuilder();
    }

    @Override
    public void exitSteps_def(LazyTesterParser.Steps_defContext ctx) {
        Step step = stepBuilder.build();
        steps.add(step);
    }

    public Scenario getScenario() {
        return scenario;
    }
}
