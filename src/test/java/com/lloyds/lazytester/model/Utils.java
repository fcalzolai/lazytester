package com.lloyds.lazytester.model;

import com.lloyds.ParseYmlFileTest;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URL;

public class Utils {

    public static <T> T parse(String toParse, Class<T> clazz) {
        StringReader sr = new StringReader(toParse);
        Yaml yaml = new Yaml(new Constructor(clazz));
        T load = yaml.load(sr);
//        System.out.println("\n\n++++++++++++++++++");
//        System.out.println(load);
        return load;
    }

    public static <T> T parse(URL url, Class<T> clazz) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(clazz));
        T load = yaml.load(new FileReader(url.getPath()));
//        System.out.println("\n\n++++++++++++++++++");
//        System.out.println(load);
        return load;
    }
}
