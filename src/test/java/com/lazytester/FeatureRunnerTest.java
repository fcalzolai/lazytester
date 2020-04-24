package com.lazytester;

import com.lazytester.model.Feature;
import com.lazytester.model.Utils;
import com.lazytester.runner.FeatureResult;
import com.lazytester.runner.FeatureRunner;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureRunnerTest {

    private static final String FEATURE_1 =
            "steps: \n" +
            "   - name: get\n" +
            "     operation: GET \n" +
            "     loop: 4\n" +
            "     url: \"http://jsonplaceholder.typicode.com\" \n" +
            "     assertions: \n" +
            "         status: 200\n" +
            "\n" +
            "scenarios:\n" +
            "   - name: As simple GET request response\n" +
            "     loop: 1\n" +
            "     steps:\n" +
            "         - get\n" +
            "";

    private static final String FEATURE_2 =
            "steps: \n" +
            "   - name: get\n" +
            "     operation: GET \n" +
            "     loop: 4\n" +
            "     url: \"http://jsonplaceholder.typicode.com\" \n" +
            "     assertions: \n" +
            "         status: 200\n" +
            "         headers: \n" +
            "            Transfer-Encoding: chunked" +
            "\n" +
            "scenarios:\n" +
            "   - name: As simple GET request response\n" +
            "     loop: 1\n" +
            "     steps:\n" +
            "         - get\n" +
            "";

    private static final String FEATURE_3 =
            "steps: \n" +
            "   - name: get\n" +
            "     operation: GET \n" +
            "     loop: 4\n" +
            "     url: \"http://jsonplaceholder.typicode.com\" \n" +
            "     assertions: \n" +
            "         status: 200\n" +
            "         body: \n" +
            "            doc: <!DOCTYPE html>" +
            "\n" +
            "scenarios:\n" +
            "   - name: As simple GET request response\n" +
            "     loop: 1\n" +
            "     steps:\n" +
            "         - get\n" +
            "";

    private static final String FEATURE_4 =
            "steps: \n" +
            "   - name: get\n" +
            "     operation: GET \n" +
            "     loop: 2\n" +
            "     url: \"https://jsonplaceholder.typicode.com/todos/1\" \n" +
            "     assertions: \n" +
            "         status: 200\n" +
            "         body: \n" +
            "            contains: \"userId\"\n" +
            "            xpath: $[?(@.userId==1)]\n" +
            "scenarios:\n" +
            "   - name: As simple GET request response\n" +
            "     loop: 1\n" +
            "     steps:\n" +
            "         - get\n" +
            "";

    @Test
    public void featureCreation() throws IOException {
        FeatureResult results = runFeature(FEATURE_1);
        assertEquals(4, results.columnKeySet().size());
        assertEquals(1, results.rowKeySet().size());
    }

    @Test
    public void featureCreation_2() throws IOException {
        FeatureResult results = runFeature(FEATURE_2);
        assertEquals(4, results.columnKeySet().size());
        assertEquals(1, results.rowKeySet().size());
    }

    @Test
    public void featureCreation_3() throws IOException {
        FeatureResult results = runFeature(FEATURE_3);
        assertEquals(4, results.columnKeySet().size());
        assertEquals(1, results.rowKeySet().size());
    }

    @Test
    public void featureCreation_4() throws IOException {
        FeatureResult results = runFeature(FEATURE_4);
        assertEquals(1, results.rowKeySet().size());
        assertEquals(2, results.columnKeySet().size());
    }

    private FeatureResult runFeature(String feature4) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        Feature feature = Utils.parse(feature4, Feature.class);

        FeatureRunner featureRunner = new FeatureRunner(httpClient, feature);
        featureRunner.runFeature();

        FeatureResult results = featureRunner.getResults();
        System.out.println(results);
        return results;
    }
}