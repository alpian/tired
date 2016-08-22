package com.ind.tired.web.documentation;

import com.ind.tired.web.html.HtmlWriter;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class RouteDescription {
    public static Describable of(String text, Map<String, String> parameterDescriptions) {
        return of(text, parameterDescriptions, Describable.plaintextHtml());
    }

    public static Describable of(String text, Map<String, String> parameterDescriptions, Describable content) {
        return of(text, parameterDescriptions, content, newHashMap());
    }

    public static Describable of(String text, Describable content) {
        return of(text, content, newHashMap());
    }

    public static Describable of(String text, Describable content, Map<Integer, String> statusCodes) {
        return of(text, newHashMap(), content, statusCodes);
    }

    public static Describable of(String text, Map<String, String> parameterDescriptions, Describable content, Map<Integer, String> statusCodes) {
        return new FullRouteDescription(text, parameterDescriptions, content, statusCodes);
    }

    public static Describable of(String text) {
        return of(text, newHashMap());
    }

    public static Describable none() {
        return new NoRouteDescription();
    }

    public static class NoRouteDescription implements Describable {
        @Override
        public void describeTo(HtmlWriter h) {

        }
    }
}
