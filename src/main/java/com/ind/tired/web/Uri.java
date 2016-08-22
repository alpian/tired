package com.ind.tired.web;

import java.net.URI;
import java.util.Optional;

import static java.lang.String.format;

public class Uri {
    public final String host;
    public final int port;
    public final String protocol;

    public Uri(String host, int port) {
        this.host = host;
        this.port = port;
        this.protocol = "http";
    }

    public URI base(WebRequest request) {
        final Optional<Integer> portInteger = request.header("X-Forwarded-Port").map(Integer::parseInt);
        return URI.create(format("%s://%s:%d/",
                request.header("X-Forwarded-Proto").orElse(protocol),
                request.header("X-Forwarded-Host").orElse(host),
                portInteger.orElse(port)));
    }
}
