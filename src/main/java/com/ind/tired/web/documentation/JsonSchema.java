package com.ind.tired.web.documentation;

import com.ind.tired.web.html.HtmlWriter;
import com.ind.tired.util.Strings;

import java.io.InputStream;

import static java.lang.String.format;

class JsonSchema implements Describable {
    private final String filename;

    public JsonSchema(String filename) {
        this.filename = filename;
    }

    public String asString() {
        return Strings.slurp(input());
    }

    private InputStream input() {
        return getClass().getClassLoader().getResourceAsStream(format("json/schema/%s", filename));
    }

    @Override
    public void describeTo(HtmlWriter h) {
        h.jsonCode(asString());
    }
}
