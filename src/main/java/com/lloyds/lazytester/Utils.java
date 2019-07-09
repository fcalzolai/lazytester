package com.lloyds.lazytester;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static <T> T parse(String path, Class<T> clazz) throws FileNotFoundException {
        URL url = Utils.class.getClassLoader().getResource(path);
        return parse(url, clazz);
    }

    public static <T> T parse(URL url, Class<T> clazz) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(new FileReader(url.getPath()));
    }

    public static <T> T parseStringAs(String toParse, Class<T> clazz) {
        StringReader sr = new StringReader(toParse);
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(sr);
    }

    public static <T> T parse(LinkedList<URL> list, Class<T> clazz) throws IOException {
        Yaml yaml = new Yaml(new Constructor(clazz));
        return yaml.load(createSequenceInputStream(list));
    }

    public static SequenceInputStream createSequenceInputStream(LinkedList<URL> list) throws IOException {
        Vector<InputStream> vis = new Vector<>();
        for (URL url:  list) {
            InputStream inputStream = url.openStream();
            vis.add(inputStream);
        }

        return new SequenceInputStream(vis.elements());
    }

    //TODO Really bad implementation. Reimplement at the first occasion.
    public static LinkedList<URL> extractAllIncludes(URL url) {
        LinkedList<URL> paths = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(url.getPath()))) {
            String line = br.readLine();
            while (line != null) {
                String trim = line.trim();
                if (trim.startsWith(INCLUDE)) {
                    LinkedList<URL> subpaths = extractSubsequentInclude(br);
                    LinkedList<URL> collect = subpaths.stream()
                            .map(Utils::extractAllIncludes)
                            .collect(LinkedList::new, LinkedList::addAll, LinkedList::addAll);
                    paths.addAll(collect);
                }
                line = br.readLine();
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        paths.add(url);
        return paths;
    }

    ///////////////////////////////////////////////////////

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String readFile(URL url) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(url.getPath()));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public static Map<String, String> extractRootValues(String feature) {
        int includeIndex = indexOfNextLine(feature, Pattern.compile("\ninclude|^include"));
        int stepsIndex = indexOfNextLine(feature, Pattern.compile("\nsteps|^steps"));
        int scenariosIndex = indexOfNextLine(feature, Pattern.compile("\nscenarios|^scenarios"));

        String includes = subString(feature, includeIndex, stepsIndex, scenariosIndex);
        String steps = subString(feature, stepsIndex, includeIndex, scenariosIndex);
        String scenarios = subString(feature, scenariosIndex, includeIndex, stepsIndex);

        HashMap<String, String> result = new HashMap<>();
        result.put("include", includes);
        result.put("steps", steps);
        result.put("scenarios", scenarios);
        return result;
    }

    private static String subString(String feature, int start, int end1, int end2) {
        if (start == -1) {
            return "";
        }

        int end = getMaxOrDefault(start, end1, end2, feature.length());
        return feature.substring(start, end);
    }

    private static int getMaxOrDefault(int start, int end1, int end2, int defaultVal) {
        int max = Math.max(Math.max(end1, end2), start);
        return max != start? max : defaultVal;
    }

    private static int indexOfNextLine(String string, Pattern pattern) {
        Matcher m = pattern.matcher(string);
        if(!m.find()){
            return -1;
        } else {
            return string.indexOf("\n", m.end()+1);
        }
    }

    private static LinkedList<URL> extractSubsequentInclude(BufferedReader br) throws IOException {
        LinkedList<URL> paths = new LinkedList<>();
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

