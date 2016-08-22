package com.ind.tired.web;


import com.fasterxml.jackson.databind.JsonNode;
import com.ind.tired.util.QueryParameters;
import com.ind.tired.util.Try;
import com.ind.tired.web.json.Decoders;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class WebRequest {
    public final Method method;
    public final String uri;
    private final Map<String, String> uriParameters;
    private final HttpServletRequest request;

    public WebRequest(Method method, String uri, Map<String, String> uriParameters, HttpServletRequest request) {
        this.method = method;
        this.uri = uri;
        this.uriParameters = uriParameters;
        this.request = request;
    }

    public String uriParameter(String name) {
        return uriParameters.get(name);
    }

    public Optional<String> queryParameter(String name) {
        return QueryParameters.extract(request.getQueryString(), name);
    }

    @Override
    public String toString() {
        return "WebRequest(" + method + ", " + uri + ", " + uriParameters + ")";
    }

    public Try<Reader> read() {
        return Try.doing(this::readInputStream);
    }

    public Try<JsonNode> readJsonBody() {
        return read().map(Decoders::read);
    }

    private Reader readInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
    }

    public Optional<String> header(String name) {
        return ofNullable(request.getHeader(name));
    }
}
