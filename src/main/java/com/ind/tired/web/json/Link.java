package com.ind.tired.web.json;

import java.net.URI;

import static java.lang.String.format;

public class Link {
    public final String rel;
    public final URI href;

    public Link(String rel, URI href) {
        this.rel = rel;
        this.href = href;
    }

    public static Link of(String rel, URI uri) {
        return new Link(rel, uri);
    }

    public static Link self(URI uri) {
        return of("self", uri);
    }

    public static Link first(URI uri) {
        return of("first", uri.resolve("0"));
    }

    public static Link last(URI uri, int total) {
        return of("last", uri.resolve(format("%d", total - 1)));
    }

    public static Link next(URI uri, int index, int total, int itemsPerPage) {
        return of("next", uri.resolve(format("%d/forward/%d", index, Math.min(total - index, itemsPerPage))));
    }

    public static Link head(URI base, int itemsPerPage, int total) {
        return of("head", base.resolve(format("0/forward/%d", Math.min(total, itemsPerPage))));
    }
}
