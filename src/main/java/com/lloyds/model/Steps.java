package com.lloyds.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Steps {

    @JsonProperty
    private List<StepWrapper> steps;

    @Override
    public String toString() {
        return "Steps{" +
                "steps=" + steps +
                '}';
    }
}
