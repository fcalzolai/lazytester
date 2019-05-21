package com.lloyds.ymlmodel;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class Assertions {

    private Integer status;
    private Map<String, String> headers;
    private Map<String, String> body;

    public Optional<Integer> getOptionalStatus() {
        return Optional.ofNullable(status);
    }
}
