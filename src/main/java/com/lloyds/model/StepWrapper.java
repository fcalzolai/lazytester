package com.lloyds.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StepWrapper {

    @JsonProperty
    private Step2 step;

    @Override
    public String toString() {
        return "StepWrapper{" +
                "step=" + step +
                '}';
    }
}
