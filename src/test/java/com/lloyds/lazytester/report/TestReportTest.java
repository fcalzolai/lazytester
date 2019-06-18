package com.lloyds.lazytester.report;

import com.google.common.collect.Table;
import com.lloyds.lazytester.FeatureRunner;
import com.lloyds.lazytester.model.Feature;
import com.lloyds.lazytester.model.Utils;
import com.lloyds.lazytester.validator.ValidatedAssertions;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestReportTest {

    private static final String SCENARIO = "steps: \n" +
            "   - step: \n" +
            "       name: get\n" +
            "       operation: GET \n" +
            "       loop: 2\n" +
            "       url: \"https://jsonplaceholder.typicode.com/todos/1\" \n" +
            "       assertions: \n" +
            "           status: 200\n" +
            "           body: \n" +
            "              contains: \"userId\"\n" +
            "              xpath: $[?(@.userId==1)]\n" +
            "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 1\n" +
            "       steps:\n" +
            "           - get\n" +
            "";

    private File csvFile;

    @Before
    public void before() throws IOException {
        csvFile = File.createTempFile("TestReport", ".csv");
    }

    @After
    public void after(){
        csvFile.delete();
    }

    @Test
    public void saveAsCsv() throws IOException {
        Table<Integer, Integer, ValidatedAssertions> results = runFeature(SCENARIO);

        String csvpath = csvFile.getAbsolutePath();
        File file = new File(csvpath);
        TestReport testReport = new TestReport(file);
        testReport.accept(results);

        String csvLines = readFile(csvpath, Charset.forName("UTF-8"));
        int rows = StringUtils.countMatches(csvLines, "\n");
        int actualRows = results.columnKeySet().size();
        Assert.assertEquals(actualRows, rows);
    }

    private Table<Integer, Integer, ValidatedAssertions> runFeature(String scenario) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        Feature feature = Utils.parse(scenario, Feature.class);

        FeatureRunner featureRunner = new FeatureRunner(httpClient, feature);
        featureRunner.runFeature();

        return featureRunner.getResults();
    }

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}