package com.lloyds.lazytester.runner;

import com.google.common.collect.ForwardingTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.lloyds.lazytester.validator.ValidatedAssertions;

public class FeatureResult extends ForwardingTable<Integer, Integer, ValidatedAssertions> {

    private Table<Integer, Integer, ValidatedAssertions> delegate = TreeBasedTable.create();

    @Override
    protected Table<Integer, Integer, ValidatedAssertions> delegate() {
        return delegate;
    }
}
