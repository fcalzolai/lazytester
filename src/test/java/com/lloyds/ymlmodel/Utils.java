package com.lloyds.ymlmodel;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.StringReader;

public class Utils {

    public static void parse(String toParse, Class clazz) {
        StringReader sr = new StringReader(toParse);
        Yaml yaml = new Yaml(new Constructor(clazz));
        Object load = yaml.load(sr);
        System.out.println("\n\n++++++++++++++++++");
        System.out.println(load);
    }
}
