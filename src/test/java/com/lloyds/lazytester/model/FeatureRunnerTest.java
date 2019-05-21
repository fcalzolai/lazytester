package com.lloyds.lazytester.model;

import com.lloyds.lazytester.FeatureRunner;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

public class FeatureRunnerTest {

    private static final String FEATURE_1 = "steps: \n" +
            "   - step: \n" +
            "       name: get\n" +
            "       operation: GET \n" +
            "       loop: 2\n" +
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

    @Test
    public void featureCreation() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        Feature parse = Utils.parse(FEATURE_1, Feature.class);

        FeatureRunner featureRunner = new FeatureRunner(httpClient, parse);
        featureRunner.runScenario();
    }
}