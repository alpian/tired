package com.ind.tired.web.documentation;

import com.google.common.collect.ImmutableMap;
import com.ind.tired.web.html.HtmlWriter;

import java.util.Map;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

class FullRouteDescription implements Describable {
    private final String text;
    private final Map<String, String> parameterDescriptions;
    private final Describable content;
    private final Map<Integer, String> statusCodes;

    public FullRouteDescription(String text, Map<String, String> parameterDescriptions, Describable content, Map<Integer, String> statusCodes) {
        this.text = text;
        this.parameterDescriptions = parameterDescriptions;
        this.content = content;
        this.statusCodes = statusCodes;
    }

    @Override
    public void describeTo(HtmlWriter h) {
        h.div("route-description", text);
        if (!parameterDescriptions.isEmpty()) {
            h.h4("Parameters");
            h.ul(() -> parameterDescriptions.forEach((name, explanation) ->
                    h.li(() ->
                            h.span("uri-parameter", format("%s:", name))
                             .nbsp()
                             .span("uri-parameter-explanation", explanation))));
        }
        content.describeTo(h);
        if (!statusCodes.isEmpty()) {
            h.h4("HTTP Response Status Codes");
            h.ul(() -> statusCodes.forEach((code, explanation) ->
                    h.li(() ->
                            h.span("status-code", valueOf(code))
                             .nbsp()
                             .span("status-code-description", format("(%s):", describe(code)))
                             .nbsp()
                             .span("status-code-explanation", explanation))));
        }
    }

    private String describe(int code) {
        return codeDescription.getOrDefault(code, "Unknown");
    }

    private static Map<Integer, String> codeDescription = new ImmutableMap.Builder<Integer, String>()
            .put(SC_OK, "OK")
            .put(SC_CREATED, "Created")
            .put(SC_NO_CONTENT, "No content")
            .put(SC_BAD_REQUEST, "Bad request")
            .put(SC_NOT_FOUND, "Not found")
            .put(SC_INTERNAL_SERVER_ERROR, "Internal server error")
            .build();
}
