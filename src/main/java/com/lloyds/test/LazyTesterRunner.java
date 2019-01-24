package com.lloyds.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LazyTesterRunner extends BlockJUnit4ClassRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LazyTesterRunner.class);

    private static ObjectMapper objectMapper;
    private LazyTesterMultiStepsScenarioRunner multiStepsRunner;

    public LazyTesterRunner(Class<?> klass) throws InitializationError {
        super(klass);
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.registerModule(new Jdk8Module());
        multiStepsRunner = new LazyTesterMultiStepsScenarioRunner(objectMapper);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {

        final Description description = describeChild(method);
        JsonTestCase annotation = method.getMethod().getAnnotation(JsonTestCase.class);

        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else if (annotation != null) {
            runLeafJsonTest(notifier, description, annotation);
        } else {
            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // It is an usual Junit test, not the JSON test case
            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            runLeafJUnitTest(methodBlock(method), description, notifier);
        }

    }

    public <T> T jsonFileToJava(String jsonFileName, Class<T> clazz) throws IOException {
        String string = Utils.readJsonAsString(jsonFileName);
        return objectMapper.readValue(string, clazz);
    }

    private void runLeafJsonTest(RunNotifier notifier, Description description, JsonTestCase annotation) {
        LOGGER.trace("Running a json JUnit test...");
        String currentTestCase = annotation.value();

        notifier.fireTestStarted(description);

        try {
            ScenarioSpec scenarioSpec = jsonFileToJava(currentTestCase, ScenarioSpec.class);
            multiStepsRunner.runScenario(scenarioSpec, notifier, description);
        } catch (Exception ioEx) {
            ioEx.printStackTrace();
            notifier.fireTestFailure(new Failure(description, ioEx));
        }

        notifier.fireTestFinished(description);
    }

    private final void runLeafJUnitTest(Statement statement, Description description, RunNotifier notifier) {
        LOGGER.trace("Running a pure JUnit test...");

        EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
        eachNotifier.fireTestStarted();

        try {
            statement.evaluate();
            LOGGER.trace("JUnit test passed = {} ", true);
        } catch (AssumptionViolatedException e) {
            LOGGER.trace("JUnit test failed due to : {},  passed = {}", e, false);
            eachNotifier.addFailedAssumption(e);
        } catch (Throwable e) {
            LOGGER.trace("JUnit test failed due to : {},  passed = {}", e, false);
            eachNotifier.addFailure(e);
        }
            eachNotifier.fireTestFinished();
    }
}
