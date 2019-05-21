package com.lloyds.ymlmodel;

public class StepWrapper {

    private Step step;

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "StepWrapper{" +
                "step=" + step +
                '}';
    }
}
