package com.lloyds.lazytester;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;

public class UtilsTest {

    private static final String INCLUDE_EXT = "yaml/model/featureExtended.yaml";

    @Test
    public void extractAllIncludes() {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT);
        LinkedHashSet<URL> paths = Utils.extractAllIncludes(url);
        Assert.assertEquals(1, paths.size());
    }
}