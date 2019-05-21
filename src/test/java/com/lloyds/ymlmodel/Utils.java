package com.lloyds.ymlmodel;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.StringReader;

public class Utils {

    public static <T> T parse(String toParse, Class<T> clazz) {
        StringReader sr = new StringReader(toParse);
        Yaml yaml = new Yaml(new Constructor(clazz));
        T load = yaml.load(sr);
        System.out.println("\n\n++++++++++++++++++");
        System.out.println(load);
        return load;
    }
}
