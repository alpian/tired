package com.ind.tired.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class QueryParameter {
    public final String key;
    public final String valueName;

    public QueryParameter(String key, String valueName) {
        this.key = key;
        this.valueName = valueName;
    }

    @Override
    public String toString() {
        return format("[%s=${%s}]", key, valueName);
    }

    public static QueryParameter from(String description) {
        // description: [key=${valueName}]
        final Matcher matcher = Pattern.compile("^\\[(?<key>\\w+)=\\$\\{(?<valueName>\\w+)\\}\\]$").matcher(description);
        matcher.matches();
        return new QueryParameter(matcher.group("key"), matcher.group("valueName"));
    }
}
