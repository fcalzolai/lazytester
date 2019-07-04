package com.lloyds.lazytester;

import com.google.common.collect.Lists;
import com.lloyds.lazytester.model.Feature;
import com.lloyds.lazytester.model.Steps;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.events.DocumentStartEvent;
import org.yaml.snakeyaml.events.Event;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;

import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    private static final String INCLUDE = "yaml/model/feature.yaml";
    private static final String INCLUDE_EXT_1 = "yaml/model/featureExt1.yaml";
    private static final String INCLUDE_EXT_2 = "yaml/model/featureExt2.yaml";
    private static final String INCLUDE_EXT_1_1 = "yaml/model/featureExt1_1.yaml";
    private static final String STEPS = "steps";

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

        Feature parse = Utils.parse(paths, Feature.class);
        System.out.println(parse);
    }

    @Test
    public void parseSequenceInputStream_2() throws IOException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(INCLUDE_EXT_1_1);
        Yaml yaml = new Yaml(new Constructor(Feature.class));
        Iterable<Node> compose = yaml.composeAll(new FileReader(file.getPath()));
        LinkedList<Object> list = Lists.newLinkedList(compose);
        System.out.println(list);
    }

    @Test
    public void parseSequenceInputStream_4() throws IOException {
        URL file = ParseYmlFileTest.class.getClassLoader().getResource(INCLUDE_EXT_1_1);
        Yaml yaml = new Yaml(new Constructor(Feature.class));
        Iterable<Event> compose = yaml.parse(new FileReader(file.getPath()));
        LinkedList<Event> list = Lists.newLinkedList(compose);

        Event startSteps = null;
        for (Event event: list) {
            Mark startMark = event.getStartMark();
            if(isSteps(event) && startSteps == null){
                startSteps = event;
            }
            System.out.println(startMark); //TODO here!!!!
        }
        System.out.println(list);
    }

    private boolean isSteps(Event event) {
        return event.getStartMark().get_snippet().contains(STEPS);
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
}