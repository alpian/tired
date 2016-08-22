package com.ind.tired.web.documentation;

import com.ind.tired.web.html.HtmlWriter;

public interface Describable {
    void describeTo(HtmlWriter h);

    static Describable plaintextHtml() {
        return h -> h.h3("Response content").p(() -> h.span("code-like", "text/html"));
    }
}
