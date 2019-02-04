package com.lloyds.composite;

import antlr.lazytester.autogen.LazyTesterBaseListener;
import antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.composite.Scenario;
import com.lloyds.composite.Step;

import java.util.LinkedList;

public class ScenarioListener extends LazyTesterBaseListener {

    private Scenario.ScenarioBuilder scenarioBuilder;
    private Scenario scenario;
    private boolean isInStep;
    private LinkedList<Step> steps;

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
        if(isInStep){
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

    public Scenario getScenario() {
        return scenario;
    }
}
