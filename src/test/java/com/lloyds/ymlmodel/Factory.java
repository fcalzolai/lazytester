package com.lloyds.ymlmodel;

import com.lloyds.utils.Utils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Factory {

    private static final String PATH1 = "yaml/steps.yaml";
    private static final String PATH2 = "yaml/anchor.yaml";

    public static void main(String[] args) throws IOException {
        buildAndPrint(PATH1);
        buildAndPrint(PATH2);
    }

    private static void buildAndPrint(String path) throws FileNotFoundException {
        URL is = Utils.class.getClassLoader().getResource(path);
        Yaml yaml = new Yaml();
        Object load = yaml.load(new FileReader(is.getPath()));
        System.out.println("\n\n++++++++++++++++++");
        System.out.println("File: " + path);
        System.out.println(load);
    }
}
