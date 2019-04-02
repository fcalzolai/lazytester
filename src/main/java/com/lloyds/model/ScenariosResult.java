package com.lloyds.model;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ScenariosResult {

    private Map<Scenario, Table<Integer, Integer, ValidatedAssertions>> results;

    public ScenariosResult() {
        results = new HashMap<>();
    }

    public ValidatedAssertions put(Scenario scenario, int i, int j, ValidatedAssertions validatedAssertions) {
        Table<Integer, Integer, ValidatedAssertions> scenarioResult = results.computeIfAbsent(scenario, s -> TreeBasedTable.create());
        return scenarioResult.put(i, j, validatedAssertions);
    }

    public Table<Integer, Integer, ValidatedAssertions> get(Scenario scenario) {
        return results.get(scenario);
    }

    public void forEachScenario(BiConsumer<? super Scenario, ? super Table<Integer, Integer, ValidatedAssertions>> action){
        results.forEach(action);
    }
}
