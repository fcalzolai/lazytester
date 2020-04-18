package com.lazytester.event;

import com.google.common.collect.Lists;
import com.lazytester.ParseYmlFileTest;
import com.lazytester.model.ModelFeature;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.nodes.Node;

import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    private static final String INCLUDE = "yaml/model/feature.yaml";
    private static final String INCLUDE_EXT_1 = "yaml/model/featureExt1.yaml";
    private static final String INCLUDE_EXT_2 = "yaml/model/featureExt2.yaml";
    private static final String INCLUDE_EXT_1_1 = "yaml/model/featureExt1_1.yaml";

    @Test
    public void extractAllIncludes_1() {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_1);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);
        assertEquals(2, paths.size());
    }

    @Test
    public void extractAllIncludes_2() {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);
        assertEquals(3, paths.size());
    }

    @Test
    public void createSequenceInputStream() throws IOException {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);

        SequenceInputStream sequenceInputStream = Utils.createSequenceInputStream(paths);

        String s = IOUtils.toString(sequenceInputStream, StandardCharsets.UTF_8);
        System.out.println(s);
    }

    @Test
    public void parseSequenceInputStream() throws IOException {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);

        ModelFeature parse = Utils.parse(paths, ModelFeature.class);
        System.out.println(parse);
    }

    @Test
    public void parseSequenceInputStream_2() throws IOException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(INCLUDE_EXT_1_1);
        Yaml yaml = new Yaml(new Constructor(ModelFeature.class));
        Iterable<Node> compose = yaml.composeAll(new FileReader(file.getPath()));
        LinkedList<Object> list = Lists.newLinkedList(compose);
        System.out.println(list);
    }

    @Test
    public void parseSequenceInputStream_3() {
        Yaml yaml = new Yaml();
        Iterable<Node> compose = yaml.composeAll(new StringReader("abc: 56\n---\n123\n---\n456"));
        LinkedList<Object> list = Lists.newLinkedList(compose);
        System.out.println(list);
    }

    @Test
    public void testComposeAllFromReader() {
        Yaml yaml = new Yaml();
        Iterable<Node> nodes = yaml.composeAll(new StringReader("abc: 56\n---\n123\n---\n456"));
        LinkedList<Object> list = Lists.newLinkedList(nodes);
        System.out.println(list);
    }

    @Test
    public void testComposeAllFromReader3() {
        Yaml yaml = new Yaml();
        Iterable<Event> parse = yaml.parse(new StringReader("abc: 56\n---\n4\n---\nqwe\n"));
        LinkedList<Object> list = Lists.newLinkedList(parse);
        System.out.println(list);
    }

    @Test
    public void parseSequenceInputStream_4() throws IOException {
        URL baseUrl = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> urls = Utils.extractAllIncludes(baseUrl);

        HashMap<String, String> result = new HashMap<>();
        for (URL url: urls) {
            String fileContent = Utils.readFile(url);
            Map<String, String> rootValues = Utils.extractRootValues(fileContent);
            rootValues.forEach((key, value) -> result.put(key, result.getOrDefault(key, "") + value));
        }
        System.out.println(result);
    }

    @Test
    public void extractIncludeValues() {
        String feature = "include:  \n" +
                "  - yaml/model/feature.yaml\n" +
                "  - yaml/model/simple.yaml\n";
        Map<String, String> root = Utils.extractRootValues(feature);
        Assert.assertTrue(root.get("include").length() > 0);
        Assert.assertTrue(root.get("steps").length() == 0);
        Assert.assertTrue(root.get("scenarios").length() == 0);
    }

    @Test
    public void extractStepsValues() {
        String feature = "steps:\n" +
                "  - step:\n" +
                "      name: google\n" +
                "      loop: 3\n" +
                "      url: http://www.google.com\n" +
                "\n" +
                "  - step:\n" +
                "      name: yahoo\n" +
                "      loop: 1\n" +
                "      url: http://www.yahoo.com\n" +
                "\n" +
                "  - step:\n" +
                "      name: test\n" +
                "      loop: 1\n";
        Map<String, String> root = Utils.extractRootValues(feature);
        Assert.assertTrue(root.get("include").length() == 0);
        Assert.assertTrue(root.get("steps").length() > 0);
        Assert.assertTrue(root.get("scenarios").length() == 0);
    }

    @Test
    public void extractScenariosValues() {
        String feature = "scenarios:\n" +
                "  - scenario:\n" +
                "      name: As simple GET request response\n" +
                "      loop: 2\n" +
                "      steps:\n" +
                "        - getGoogle";
        Map<String, String> root = Utils.extractRootValues(feature);
        Assert.assertTrue(root.get("include").length() == 0);
        Assert.assertTrue(root.get("steps").length() == 0);
        Assert.assertTrue(root.get("scenarios").length() > 0);
    }

    @Test
    public void extractRootValues() {
        String feature = "steps:\n" +
                "  - step: &google\n" +
                "      name: google\n" +
                "      loop: 3\n" +
                "      url: http://www.google.com\n" +
                "      assertions:\n" +
                "        status: 200\n" +
                "\n" +
                "  - step: &getGoogle\n" +
                "      <<: *google\n" +
                "      name: getGoogle\n" +
                "      operation: GET\n" +
                "\n" +
                "  - step: &postGoogle\n" +
                "      <<: *google\n" +
                "      name: postGoogle\n" +
                "      operation: POST\n" +
                "      loop: 5\n" +
                "\n" +
                "scenarios:\n" +
                "  - scenario: &getFeature\n" +
                "      name: As simple GET request response\n" +
                "      loop: 2\n" +
                "      steps:\n" +
                "        - getGoogle\n" +
                "        - postGoogle\n" +
                "\n" +
                "  - scenario: &getAndPostScenario\n" +
                "      name: GET and POST scenario\n" +
                "      loop: 2\n" +
                "      steps:\n" +
                "        - getGoogle\n" +
                "        - postGoogle";
        Map<String, String> root = Utils.extractRootValues(feature);
        Assert.assertTrue(root.get("include").length() == 0);
        Assert.assertTrue(root.get("steps").length() > 0);
        Assert.assertTrue(root.get("scenarios").length() > 0);
    }
}