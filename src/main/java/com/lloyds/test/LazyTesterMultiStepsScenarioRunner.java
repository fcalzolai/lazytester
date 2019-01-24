package com.lloyds.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class LazyTesterMultiStepsScenarioRunner {

    private static final Logger LOGGER = getLogger(LazyTesterMultiStepsScenarioRunner.class);

    private ExternalFileProcessor extFileProcessor;

    public LazyTesterMultiStepsScenarioRunner(ObjectMapper objectMapper) {
        this.extFileProcessor = new ExternalFileProcessor(objectMapper);
    }

    public synchronized boolean runScenario(ScenarioSpec scenario, RunNotifier notifier, Description description) {
        LOGGER.info("\n-------------------------- BDD: Scenario:{} -------------------------\n", scenario.getScenarioName());

        final int scenarioLoopTimes = scenario.getLoop().orElse(1);

        for (int k = 0; k < scenarioLoopTimes; k++) {

            LOGGER.info("### Executing Scenario -->> Count No: {}", k);

            for (Step thisStep : scenario.getSteps()) {
                final int stepLoopTimes = thisStep.getLoop().orElse(1);
                for (int i = 0; i < stepLoopTimes; i++) {
                    thisStep = extFileProcessor.resolveExtJsonFile(thisStep);
                    LOGGER.info("### Executing Step [{}]-->> Count No: {}", thisStep, i);
                }
            }
        }
        return true;
    }
}
