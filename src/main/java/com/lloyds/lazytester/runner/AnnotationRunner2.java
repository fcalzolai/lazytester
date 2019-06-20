package com.lloyds.lazytester.runner;

import com.lloyds.lazytester.model.Feature;
import org.apache.http.impl.client.HttpClients;
import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AnnotationRunner2 extends ParentRunner<FeatureRunner> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationRunner2.class);

    private static final URL BASE_URL = AnnotationRunner2.class.getClassLoader().getResource(".");
    private static final String GLOB_SYNTAX = "glob:";
    private static final String REGEX_SYNTAX = "regex:";
    private static final String DEFAULT_SYNTAX = "regex:";

    private final ConcurrentHashMap<FrameworkMethod, Description> methodDescriptions = new ConcurrentHashMap<FrameworkMethod, Description>();

    public AnnotationRunner2(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FeatureRunner> getChildren() {
        YamlUnitTestCase yamlAnnotation = getAnnotations();

        Path path = Paths.get(BASE_URL.getPath());
        PathMatcher pathMatcher = getPathMatcher(yamlAnnotation, path);

        try {
            List<Path> tests = Files.walk(path)
                    .filter(pathMatcher::matches)
                    .collect(Collectors.toList());
            System.out.println(tests);
        } catch (IOException e) {
            String filter = yamlAnnotation.features()[0];
            throw new IllegalArgumentException("Unable to find files with in ["+BASE_URL.getPath()+"] with filter ["+ filter +"]");
        }

        return null;
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        Description description = Description.createTestDescription(getTestClass().getJavaClass(),
                testName(method), method.getAnnotations());
        return description;
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        YamlUnitTestCase yamlAnnotation = getAnnotations();

        Path path = Paths.get(BASE_URL.getPath());
        PathMatcher pathMatcher = getPathMatcher(yamlAnnotation, path);

        try {
            List<Path> tests = Files.walk(path)
                    .filter(pathMatcher::matches)
                    .collect(Collectors.toList());
            System.out.println(tests);
        } catch (IOException e) {
            String filter = yamlAnnotation.features()[0];
            throw new IllegalArgumentException("Unable to find files with in ["+BASE_URL.getPath()+"] with filter ["+ filter +"]");
        }

        return super.computeTestMethods();
    }

    private PathMatcher getPathMatcher(YamlUnitTestCase yamlAnnotation, Path path) {
        String filter = yamlAnnotation.features()[0];
        if(!filter.startsWith(GLOB_SYNTAX)
            || !filter.startsWith(REGEX_SYNTAX)){
            filter = DEFAULT_SYNTAX+ filter;
        }
        return path.getFileSystem().getPathMatcher(filter);
    }

    private YamlUnitTestCase getAnnotations() {
        Annotation[] annotations = getTestClass().getAnnotations();
        List<Annotation> collect = Arrays.stream(annotations).filter(annotation -> annotation.annotationType().equals(YamlUnitTestCase.class)).collect(Collectors.toList());
        if(collect.isEmpty()){
            throw new IllegalArgumentException("Unable to find @YamlUnitTestCase annotation");
        } else if (collect.size() > 1) {
            throw new IllegalArgumentException("Unable to parse "+collect.size()+" @YamlUnitTestCase annotations");
        }

        Annotation annotation = collect.get(0);
        if (annotation instanceof YamlUnitTestCase) {
            YamlUnitTestCase yamlAnnotation = (YamlUnitTestCase) annotation;
            return yamlAnnotation;
        } else {
            throw new IllegalArgumentException("Unable to cast "+annotation.getClass()+" to YamlUnitTestCase");
        }
    }

    @Override
    protected void runChild(FeatureRunner method, RunNotifier notifier) {

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
        InputStream inputStream = AnnotationRunner2.class.getClassLoader().getResourceAsStream(fileName);
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
