package com.lazytester.model;

import lombok.Data;

@Data
public class StepWrapper {

    //TODO this class has been added just for marshalling/unmarshalling from YML.
    //  Try to find a better solution.
    private Step step;

}
