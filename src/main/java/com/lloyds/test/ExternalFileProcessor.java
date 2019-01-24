package com.lloyds.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class ExternalFileProcessor {

    private static final Logger LOGGER = getLogger(ExternalFileProcessor.class);
    private static final String JSON_PAYLOAD_FILE = "JSON.FILE:";

    private final ObjectMapper objectMapper;

    ExternalFileProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

     Step resolveExtJsonFile(Step thisStep) {
        try {
            if (!checkDigNeeded(thisStep)) {
                return thisStep;
            }
            JsonNode stepNode = objectMapper.convertValue(thisStep, JsonNode.class);
            Map<String, Object> stepMap = objectMapper.readValue(stepNode.toString(), new TypeReference<Map<String, Object>>() {});
            digReplaceContent(stepMap);
            JsonNode jsonStepNode = objectMapper.valueToTree(stepMap);
            return objectMapper.treeToValue(jsonStepNode, Step.class);
        } catch (Exception exx) {
            LOGGER.error("External file reading exception - {}", exx.getMessage());
            throw new RuntimeException("External file reading exception. Details - " + exx);
        }
    }

    private void digReplaceContent(Map<String, Object> map) {
        map.entrySet().forEach(entry -> {

            Object value = entry.getValue();

            if (value instanceof Map) {
                digReplaceContent((Map<String, Object>) value);

            } else {
                LOGGER.debug("Leaf node found = {}, checking for any external json file...", value);
                if (value != null && value.toString().contains(JSON_PAYLOAD_FILE)) {
                    LOGGER.info("Found external JSON file place holder = {}. Replacing with content", value);
                    String valueString = value.toString();
                    String token = getJsonFilePhToken(valueString);
                    if (token != null && token.startsWith(JSON_PAYLOAD_FILE)) {
                        String resourceJsonFile = token.substring(JSON_PAYLOAD_FILE.length());
                        try {
                            Object jsonFileContent = objectMapper.readTree(Utils.readJsonAsString(resourceJsonFile));
                            entry.setValue(jsonFileContent);
                        } catch (Exception exx) {
                            LOGGER.error("External file reference exception - {}", exx.getMessage());
                            throw new RuntimeException(exx);
                        }
                    }
                }
            }
        });
    }

    private String getJsonFilePhToken(String valueString) {
        if (valueString != null) {
            List<String> allTokens = Utils.getAllTokens(valueString);
            if (allTokens != null && !allTokens.isEmpty()) {
                return allTokens.get(0);
            }
        }
        return null;
    }

    boolean checkDigNeeded(Step thisStep) throws JsonProcessingException {
        String stepJson = objectMapper.writeValueAsString(thisStep);
        List<String> allTokens = Utils.getAllTokens(stepJson);
        return allTokens.toString().contains(JSON_PAYLOAD_FILE);
    }
}
