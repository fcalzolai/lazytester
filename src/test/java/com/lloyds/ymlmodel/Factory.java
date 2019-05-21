package com.lloyds.ymlmodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloyds.utils.Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Factory {

    private static final String PATH = "yaml/steps.yaml";
    private static final String PATH2 = "yaml/anchor.yaml";

    public static void main(String[] args) throws IOException {
        URL is, is2;
        Yaml yaml;
        Steps step;
        ObjectMapper mapper;

//        is = Utils.class.getClassLoader().getResource(PATH);
//        mapper = new ObjectMapper(new YAMLFactory());
//        step = mapper.readValue(is, Steps.class);
//        System.out.println(step);
//
//        yaml = new Yaml();
//        Object load = yaml.load(new FileReader(is.getPath()));
//        System.out.println(load);
//
//        is2 = Utils.class.getClassLoader().getResource(PATH2);
//        Object load2 = yaml.load(new FileReader(is2.getPath()));
//        System.out.println(load2);
        is = Utils.class.getClassLoader().getResource(PATH2);

        yaml = new Yaml();
        Object load = yaml.load(new FileReader(is.getPath()));
        System.out.println("\n\n++++++++++++++++++");
        System.out.println(load);

//        mapper = new ObjectMapper(new YAMLFactory());
//        System.out.println(mapper.readValue(is, Steps.class));
    }
}
