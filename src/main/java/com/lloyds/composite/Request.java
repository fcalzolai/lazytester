package com.lloyds.composite;

import java.util.Optional;

public class Request {

    private Optional<String> body;
    private Optional<String> headers;
    private Optional<String> params;

    public Request() {
        this.body = Optional.empty();
        this.headers = Optional.empty();
        this.params = Optional.empty();
    }


}
