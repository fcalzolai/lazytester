package com.lazytester;

import com.lazytester.event.Utils;
import com.lazytester.model.ModelFeature;
import com.lazytester.runner.FeatureResult;
import com.lazytester.runner.FeatureRunner;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class ModelFeatureRunnerTest {

    private static final String FEATURE_1 = "steps: \n" +
            "   - step: \n" +
            "       name: get\n" +
            "       operation: GET \n" +
            "       loop: 4\n" +
            "       url: \"http://jsonplaceholder.typicode.com\" \n" +
            "       assertions: \n" +
            "           status: 200\n" +
            "\n" +
            "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 1\n" +
            "       steps:\n" +
            "           - get\n" +
            "";

    private static final String FEATURE_2 = "steps: \n" +
            "   - step: \n" +
            "       name: get\n" +
            "       operation: GET \n" +
            "       loop: 4\n" +
            "       url: \"http://jsonplaceholder.typicode.com\" \n" +
            "       assertions: \n" +
            "           status: 200\n" +
            "           headers: \n" +
            "              Via: 1.1 vegur, 1.1 10.113.60.135 (McAfee Web Gateway 7.8.0.1.0.24550)" +
            "\n" +
            "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 1\n" +
            "       steps:\n" +
            "           - get\n" +
            "";

    private static final String FEATURE_3 = "steps: \n" +
            "   - step: \n" +
            "       name: get\n" +
            "       operation: GET \n" +
            "       loop: 4\n" +
            "       url: \"http://jsonplaceholder.typicode.com\" \n" +
            "       assertions: \n" +
            "           status: 200\n" +
            "           body: \n" +
            "              doc: <!DOCTYPE html>" +
            "\n" +
            "scenarios:\n" +
            "   - scenario: \n" +
            "       name: As simple GET request response\n" +
            "       loop: 1\n" +
            "       steps:\n" +
            "           - get\n" +
            "";

    private static final String FEATURE_4 = "steps: \n" +
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

    @Test
    public void featureCreation() throws IOException {
        FeatureResult results = runFeature(FEATURE_1);
        Assert.assertEquals(4, results.columnKeySet().size());
        Assert.assertEquals(1, results.rowKeySet().size());
    }

    @Ignore
    @Test
    public void featureCreation_2() throws IOException {
        FeatureResult results = runFeature(FEATURE_2);
        Assert.assertEquals(4, results.columnKeySet().size());
        Assert.assertEquals(1, results.rowKeySet().size());
    }

    @Test
    public void featureCreation_3() throws IOException {
        FeatureResult results = runFeature(FEATURE_3);
        Assert.assertEquals(4, results.columnKeySet().size());
        Assert.assertEquals(1, results.rowKeySet().size());
    }

    @Test
    public void featureCreation_4() throws IOException {
        FeatureResult results = runFeature(FEATURE_4);
        Assert.assertEquals(1, results.rowKeySet().size());
        Assert.assertEquals(2, results.columnKeySet().size());
    }

    private FeatureResult runFeature(String featureDefinition) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        ModelFeature modelFeature = Utils.parseStringAs(featureDefinition, ModelFeature.class);

        FeatureRunner featureRunner = new FeatureRunner(httpClient, modelFeature);
        featureRunner.runFeature();

        FeatureResult results = featureRunner.getResults();
        System.out.println(results);
        return results;
    }
}