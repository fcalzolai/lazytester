package com.lloyds.lazytester;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class UtilsTest {

    private static final String INCLUDE_EXT_1 = "yaml/model/featureExt1.yaml";
    private static final String INCLUDE_EXT_2 = "yaml/model/featureExt2.yaml";

    @Test
    public void extractAllIncludes_1() {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_1);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);
        Assert.assertEquals(2, paths.size());
    }

    @Test
    public void extractAllIncludes_2() {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);
        Assert.assertEquals(3, paths.size());
    }

    @Test
    public void createSequenceInputStream() throws IOException {
        URL url = UtilsTest.class.getClassLoader().getResource(INCLUDE_EXT_2);
        LinkedList<URL> paths = Utils.extractAllIncludes(url);

        SequenceInputStream sequenceInputStream = Utils.createSequenceInputStream(paths);
        String s = IOUtils.toString(sequenceInputStream, StandardCharsets.UTF_8);
        System.out.println(s);
    }
}