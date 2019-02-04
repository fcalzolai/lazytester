package antlr.lazytester.buider;

import antlr.lazytester.autogen.LazyTesterBaseListener;
import antlr.lazytester.autogen.LazyTesterParser;
import com.lloyds.composite.Scenario;

public class ScenarioListener extends LazyTesterBaseListener {

    private Scenario.ScenarioBuilder scenarioBuilder;
    private Scenario scenario;

    @Override
    public void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx) {
        scenarioBuilder = Scenario.getScenarioBuilder();
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

    public Scenario getScenario() {
        return scenario;
    }
}
