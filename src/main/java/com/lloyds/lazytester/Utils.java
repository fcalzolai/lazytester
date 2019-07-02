package com.lloyds.lazytester;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedHashSet;

public class Utils {

    private static final String INCLUDE = "include";

    public static <T> T parse(Path path, Class<T> clazz) {
        try {
            URL url = path.toUri().toURL();
            return parse(url, clazz);
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to parse path [" + path.getFileName().toFile().getAbsolutePath() + "]");
        }
    }

    public static <T> T parse(URL url, Class<T> clazz) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(new FileReader(url.getPath()));
    }

    public static <T> T parse(String path, Class<T> clazz) throws FileNotFoundException {
        URL url = Utils.class.getClassLoader().getResource(path);
        return parse(url, clazz);
    }

    public static <T> T parseStringAs(String toParse, Class<T> clazz) {
        StringReader sr = new StringReader(toParse);
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(sr);
    }

    public static void testSequenceInputStream() {
        SequenceInputStream sis = new SequenceInputStream(null);
    }


    public static LinkedHashSet<URL> extractAllIncludes(URL url) {
        LinkedHashSet<URL> paths = new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(url.getPath()))) {
            String line = br.readLine();
            while (line != null) {
                String trim = line.trim();
                if (trim.startsWith(INCLUDE)) {
                    LinkedHashSet<URL> subpaths = extractSubsequentInclude(br);
                    paths.addAll(subpaths);
                    LinkedHashSet<URL> collect = subpaths.stream()
                            .map(Utils::extractAllIncludes)
                            .collect(LinkedHashSet::new, LinkedHashSet::addAll, LinkedHashSet::addAll);
                    paths.addAll(collect);
                }
                line = br.readLine();
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return paths;
    }

    private static LinkedHashSet<URL> extractSubsequentInclude(BufferedReader br) throws IOException {
        LinkedHashSet<URL> paths = new LinkedHashSet<>();
        String include = extractInclude(br.readLine());
        while(include != null) {
            URL url = Utils.class.getClassLoader().getResource(include);
            paths.add(url);
            include = extractInclude(br.readLine());
        }
        return paths;
    }

    private static String extractInclude(String line) {
        if(line == null) {
            return null;
        }

        if(line.trim().startsWith("-")) {
            return line.substring(line.indexOf("-") + 1).trim();
        } else {
            return null;
        }
    }
}

