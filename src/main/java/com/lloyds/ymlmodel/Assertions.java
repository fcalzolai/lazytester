package com.lloyds.ymlmodel;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class Assertions {

    private Integer status;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> body = new HashMap<>();

    public Optional<Integer> getOptionalStatus() {
        return Optional.ofNullable(status);
    }
}
