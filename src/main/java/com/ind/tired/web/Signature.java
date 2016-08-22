package com.ind.tired.web;


import com.google.common.collect.Maps;
import com.ind.tired.util.Pair;
import com.ind.tired.web.html.HtmlWriter;
import com.ind.tired.util.QueryParameters;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toSet;

public class Signature {
    public final Method method;
    public final String uri;
    private final Set<String> namedParameters;
    private final Pattern regex;
    private final Set<QueryParameter> querySignature;

    private Signature(Method method, String uriWithQueryParameters) {
        this.method = method;
        final String[] uriAndQueryParameters = uriWithQueryParameters.split("\\?", 2);
        this.uri = uriAndQueryParameters[0];
        if (uriAndQueryParameters.length > 1) {
            this.querySignature = Stream.of(uriAndQueryParameters[1].split("&")).map(QueryParameter::from).collect(toSet());
        } else {
            this.querySignature = newHashSet();
        }

        final Pattern dollarParameterRegex = Pattern.compile("^[\\w-]*\\$\\{(?<parameter>[\\w-]+)\\}[\\w-]*$");
        final String[] names = this.uri.split("/");
        namedParameters = Stream.of(names)
                .filter((s) -> dollarParameterRegex.matcher(s).matches())
                .map((s) -> {
                    final Matcher m = dollarParameterRegex.matcher(s);
                    m.matches();
                    return m.group("parameter");
                })
                .collect(toSet());

        regex = Pattern.compile("^" +
                Stream.of(names)
                .map((name) -> {
                    final Pattern p = Pattern.compile("^(?<before>[\\w-]*)\\$\\{(?<parameter>[\\w-]+)\\}(?<after>[\\w-]*)$");
                    final Matcher m = p.matcher(name);
                    if (m.matches()) {
                        return m.group("before") + "(?<" + m.group("parameter") + ">[\\w-]+)" + m.group("after");
                    } else {
                        return name;
                    }
                })
                .reduce((left, right) -> left + "/" + right).orElse("/") + "$");
    }

    @Override
    public String toString() {
        return method + " " + uri;
    }

    public static Signature of(Method method, String uri) {
        return new Signature(method, uri);
    }

    public boolean matches(Method method, String uri, String queryString) {
        return this.method == method
                && regex.matcher(uri).matches()
                && querySignature.stream().allMatch(q -> QueryParameters.extract(queryString, q.key).isPresent());
    }

    public Map<String, String> parameters(String uri) {
        final Matcher matcher = regex.matcher(uri);
        if (matcher.matches()) {
            return namedParameters.stream()
                    .map(name -> Pair.of(name, matcher.group(name)))
                    .collect(Collectors.toMap(p -> p.a, p-> p.b));
        } else {
            return Maps.newHashMap();
        }
    }

    public void describeTo(PrintWriter writer) {
        final HtmlWriter h = new HtmlWriter(writer);
        h.div("http-entry-point", () -> {
            h.span("code-like http-method", method.name())
                    .nbsp()
                    .span("code-like http-uri", uri);
            querySignature.forEach(q -> h.span("code-like http-query-parameters", "?" + q));
        });
    }
}
