package com.lazytester.event;

import com.google.common.collect.Lists;
import com.lazytester.event.ParserEventHandler;
import com.lloyds.lazytester.model.Feature;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.events.Event;

import java.io.StringReader;
import java.util.LinkedList;

public class ParserEventHandlerTest {

    private static final String STEPS_1 =
            "steps: \n" +
            "   - step:\n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "       operation: POST\n" +
            "---\n" +
            "steps: \n" +
            "   - step:\n" +
            "       name: google\n" +
            "       loop: 3\n" +
            "       url: http://www.google.com\n" +
            "       operation: DELETE\n" +
            "\n";

    @Test
    public void compose() {
        Yaml yaml = new Yaml(new Constructor(Feature.class));
        Iterable<Event> compose = yaml.parse(new StringReader(STEPS_1));
        LinkedList<Event> list = Lists.newLinkedList(compose);
        System.out.println(list);

        ParserEventHandler parserEventHandler = new ParserEventHandler(list);
        LinkedList<Event> merge = parserEventHandler.merge();
        System.out.println(merge);
    }
}