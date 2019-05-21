package com.lloyds;

import com.lloyds.utils.Utils;
import com.lloyds.ymlmodel.Steps;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class ParseYmlFile {

    private static final String STEPS = "yaml/steps.yaml";
    private static final String ANCHOR = "yaml/anchor.yaml";
    private static final String SIMPLE = "yaml/simple.yaml";
    private static final String SCENARIO = "yaml/scenario.yaml";

    public static void main(String[] args) throws IOException {
//        buildAndPrint(SIMPLE);
//        buildAndPrint(STEPS);
//        buildAndPrint(ANCHOR);
        buildAndPrint(SCENARIO);
    }

    private static void buildAndPrint(String path) throws FileNotFoundException {
        URL is = Utils.class.getClassLoader().getResource(path);
        Yaml yaml = new Yaml(new Constructor(Steps.class));
        Object load = yaml.load(new FileReader(is.getPath()));
        System.out.println("\n\n++++++++++++++++++");
        System.out.println("File: " + path);
        System.out.println(load);
    }
}
