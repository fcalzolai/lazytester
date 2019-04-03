package com.lloyds.model;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.function.Consumer;

public class ScenarioResult {

    private Table<Integer, Integer, ValidatedAssertions> table;
    private long executionTime;

    public ScenarioResult() {
        this.table = TreeBasedTable.create();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Table<Integer, Integer, ValidatedAssertions> getTable() {
        return table;
    }

    public ValidatedAssertions put(int i, int j, ValidatedAssertions validatedAssertions) {
        return table.put(i, j, validatedAssertions);
    }

    public void forEach(Consumer<? super Table.Cell<Integer, Integer, ValidatedAssertions>> consumer){
        table.cellSet().forEach(consumer);
    }
}
