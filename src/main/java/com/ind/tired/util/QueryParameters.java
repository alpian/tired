package com.ind.tired.util;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class QueryParameters {
    public static Optional<String> extract(String queryString, String key) {
        // Note that we cannot use the servlet parameter methods as they read the body looking for form parameters
        //  which prevents the body from also being read
        return ofNullable(queryString).map(qs -> {
            final MultiMap<String> parameters = new MultiMap<>();
            UrlEncoded.decodeUtf8To(qs, parameters);
            return parameters.getString(key);
        });
    }
}
