package com.lloyds.lazytester.runner;

import com.lloyds.lazytester.model.Feature;
import org.apache.http.impl.client.HttpClients;
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
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class AnnotationRunner extends BlockJUnit4ClassRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationRunner.class);

    public AnnotationRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {

        final Description description = describeChild(method);
        YamlTestCase annotation = method.getMethod().getAnnotation(YamlTestCase.class);

        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else if (annotation != null) {
            runLeafYamlTest(notifier, description, annotation);
        } else {
            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // It is an usual Junit test, not the JSON test case
            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            runLeafJUnitTest(methodBlock(method), description, notifier);
        }

    }

    //TODO move into Utils class
    private static <T> T yamlToJava(String fileName, Class<T> clazz) {
        InputStream inputStream = AnnotationRunner.class.getClassLoader().getResourceAsStream(fileName);
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(inputStream);
    }

    private void runLeafYamlTest(RunNotifier notifier, Description description, YamlTestCase annotation) {
        LOGGER.trace("Running a json Yaml test...");
        String currentTestCase = annotation.value();

        notifier.fireTestStarted(description);

        try {
            Feature feature = yamlToJava(currentTestCase, Feature.class);
            FeatureRunner fr = new FeatureRunner(HttpClients.createDefault(), feature);
            fr.runFeature();
            System.out.println(fr.getResults());
        } catch (Exception ioEx) {
            ioEx.printStackTrace();
            notifier.fireTestFailure(new Failure(description, ioEx));
        }

        notifier.fireTestFinished(description);
    }

    private void runLeafJUnitTest(Statement statement, Description description, RunNotifier notifier) {
        LOGGER.trace("Running a pure Yaml test...");

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
