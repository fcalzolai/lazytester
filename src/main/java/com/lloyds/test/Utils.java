package com.lloyds.test;

import com.google.common.io.Resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.Charset.defaultCharset;

public class Utils {

    public static String readJsonAsString(String jsonFileName) throws IOException {
        return Resources.toString(Resources.getResource(jsonFileName), defaultCharset());
    }

    public static List<String> getAllTokens(String aString) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(aString);
        List<String> keyTokens = new ArrayList<>();
        while (matcher.find()) {
            keyTokens.add(matcher.group(1));
        }

        return keyTokens;
    }
}
