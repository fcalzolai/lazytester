package com.lloyds.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class JsonServiceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonServiceExecutor.class);

    private BasicHttpClient httpClient;

    JsonServiceExecutor(ObjectMapper objectMapper) {
        this.httpClient = new BasicHttpClient(objectMapper);
    }

    String executeRESTService(String urlName, String methodName, String requestJson) throws Exception {
        HashMap queryParams = (HashMap) readJsonPathOrDefault(requestJson, "$.queryParams", new HashMap<>());
        HashMap headers = (HashMap) readJsonPathOrDefault(requestJson, "$.headers", new HashMap<>());
        Object bodyContent = readJsonPathOrDefault(requestJson, "$.body", null);

        CloseableHttpResponse serverResponse = httpClient.execute(urlName, methodName, headers, queryParams, bodyContent);
        return serverResponse.toString();
    }

    private Object readJsonPathOrDefault(String requestJson, String jsonPath, Object defaultObj) {
        try {
            return JsonPath.read(requestJson, jsonPath);
        } catch (PathNotFoundException pEx) {
            LOGGER.debug("No " + jsonPath + " was present in the request. returned null.");
            return defaultObj;
        }
    }
}
