package com.lloyds.ymlmodel;

import java.util.List;

public class Steps {

    private List<StepWrapper> steps;

    public List<StepWrapper> getSteps() {
        return steps;
    }

    public void setSteps(List<StepWrapper> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "steps=" + steps +
                '}';
    }
}
