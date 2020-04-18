package com.lazytester.runner;

import com.lazytester.model.Feature;
import org.apache.http.impl.client.HttpClients;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationRunner extends ParentRunner<FeatureRunner> {

    private static final URL BASE_URL = AnnotationRunner.class.getClassLoader().getResource(".");
    private static final String GLOB_SYNTAX = "glob:";
    private static final String REGEX_SYNTAX = "regex:";
    private static final String DEFAULT_SYNTAX = "regex:";

    private Map<FeatureRunner, String> featureToName;

    public AnnotationRunner(Class<?> klass) throws InitializationError {
        super(klass);
        featureToName = new HashMap<>();
    }

    @Override
    protected List<FeatureRunner> getChildren() {
        YamlUnitTestCase yamlAnnotation = getAnnotations();
        Path path = Paths.get(BASE_URL.getPath());
        PathMatcher pathMatcher = getPathMatcher(yamlAnnotation, path);
        List<Path> paths = getPaths(yamlAnnotation, path, pathMatcher);
        return getFeatureRunner(paths);
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        //TODO check the method public static Description createTestDescription(Class<?> clazz, String name, Annotation... annotations)

        return Description.createTestDescription(
                getTestClass().getJavaClass(),
                featureToName.get(child));
    }

    @Override
    protected void runChild(FeatureRunner featureRunner, RunNotifier notifier) {
        notifier.addListener(new RunListener());
        Description description = describeChild(featureRunner);

        notifier.fireTestStarted(description);
        try {
            featureRunner.runFeature();
            System.out.println(featureRunner.getResults());
        } catch (Exception ioEx) {
            notifier.fireTestFailure(new Failure(description, ioEx));
        }

        notifier.fireTestFinished(description);
    }

    private PathMatcher getPathMatcher(YamlUnitTestCase yamlAnnotation, Path path) {
        String filter = yamlAnnotation.features()[0];
        if(!(filter.startsWith(GLOB_SYNTAX) || filter.startsWith(REGEX_SYNTAX))){
            filter = DEFAULT_SYNTAX+ filter;
        }
        return path.getFileSystem().getPathMatcher(filter);
    }

    private YamlUnitTestCase getAnnotations() {
        Annotation[] annotations = getTestClass().getAnnotations();

        List<Annotation> collect = Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().equals(YamlUnitTestCase.class))
                .collect(Collectors.toList());

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

    //TODO move into Utils class
    private static <T> T yamlToJava(Path path, Class<Feature> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));
        try {
            InputStream io = path.toUri().toURL().openStream();
            return yaml.load(io);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load file ["+path.toUri()+"]");
        }
    }

    private List<FeatureRunner> getFeatureRunner(List<Path> paths) {
        return paths.stream().map(p -> {
            Feature feature = yamlToJava(p, Feature.class);
            FeatureRunner featureRunner = new FeatureRunner(HttpClients.createDefault(), feature);
            featureToName.put(featureRunner, p.toFile().getName());
            return featureRunner;
        }).collect(Collectors.toList());
    }

    private List<Path> getPaths(YamlUnitTestCase yamlAnnotation, Path path, PathMatcher pathMatcher) {
        try {
            return Files.walk(path)
                    .filter(pathMatcher::matches)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            String filter = yamlAnnotation.features()[0];
            throw new IllegalArgumentException("Unable to find files with in ["+BASE_URL.getPath()+"] with filter ["+ filter +"]");
        }
    }
}
