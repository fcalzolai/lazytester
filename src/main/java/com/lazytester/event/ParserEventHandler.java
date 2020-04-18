package com.lazytester.event;

import com.google.common.collect.ImmutableMap;
import org.yaml.snakeyaml.events.Event;

import java.util.LinkedList;

public class ParserEventHandler {

    private static final String STEPS = "steps";

    private final LinkedList<Event> events;

    private LinkedList<Event> beforeSteps = new LinkedList();
    private LinkedList<Event> stepsScenarios = new LinkedList();
    private LinkedList<Event> scenarioAndDocumentEnd = new LinkedList();
    private LinkedList<Event> extraSteps = new LinkedList();
    private LinkedList<Event> extraScenarios = new LinkedList();
    private final ImmutableMap<Integer, LinkedList<Event>> nexts;

    public ParserEventHandler(LinkedList<Event> events){
        this.events = events;

        nexts = ImmutableMap.<Integer, LinkedList<Event>>builder()
                .put(1, beforeSteps)
                .put(2, stepsScenarios)
                .put(3, scenarioAndDocumentEnd)
                .put(4, extraScenarios)
                .put(5, scenarioAndDocumentEnd)
                .build();
    }

    public LinkedList<Event> merge(){
        for (Event event: events) {
            add(event);
        }
        return compose();
    }

    private void add(Event event){
        System.out.println(event); //TODO here!!!!
    }

    private LinkedList<Event> compose(){
        LinkedList<Event> events = new LinkedList<>(beforeSteps);
        events.addAll(extraSteps);
        events.addAll(stepsScenarios);
        events.addAll(extraScenarios);
        events.addAll(scenarioAndDocumentEnd);
        return events;
    }

    private boolean isSteps(Event event) {
        return event.getStartMark().get_snippet().contains(STEPS);
    }
}
