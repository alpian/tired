package com.ind.tired.web.html;

import com.ind.tired.util.Procedure;

import java.io.PrintWriter;

import static java.lang.String.format;

public class HtmlWriter {
    private final PrintWriter w;

    public HtmlWriter(PrintWriter w) {
        this.w = w;
    }

    public HtmlWriter span(String cssClass, String text) {
        w.print(format("<span class=\"%s\">%s</span>", cssClass, text));
        return this;
    }

    public HtmlWriter nbsp() {
        w.print("&nbsp;");
        return this;
    }

    public HtmlWriter div(String cssClass, String text) {
        w.println(format("<div class=\"%s\">%s</div>", cssClass, text));
        return this;
    }

    public HtmlWriter div(String cssClass, Procedure p) {
        w.println(format("<div class=\"%s\">", cssClass));
        p.execute();
        w.println("</div>");
        return this;
    }

    public HtmlWriter ul(Procedure p) {
        w.println("<ul>");
        p.execute();
        w.println("</ul>");
        return this;
    }

    public HtmlWriter ul(String cssClass, Procedure p) {
        w.println(format("<ul class=\"%s\">", cssClass));
        p.execute();
        w.println("</ul>");
        return this;
    }

    public HtmlWriter li(Procedure p) {
        w.println("<li>");
        p.execute();
        w.println("</li>");
        return this;
    }

    public HtmlWriter li(String cssClass, Procedure p) {
        w.println(format("<li class=\"%s\">", cssClass));
        p.execute();
        w.println("</li>");
        return this;
    }

    public HtmlWriter nbsp(String prepend) {
        return text(prepend).nbsp();
    }

    private HtmlWriter text(String text) {
        w.print(text);
        return this;
    }

    public HtmlWriter jsonCode(String text) {
        w.print("<pre><code class=\"language-json\">");
        w.print(text);
        w.println("</code></pre>");
        return this;
    }

    public HtmlWriter h1(String text) {
        w.println(format("<h1>%s</h1>", escape(text)));
        return this;
    }

    public HtmlWriter p(String text) {
        w.println(format("<p>%s</p>", escape(text)));
        return this;
    }

    public HtmlWriter p(Procedure p) {
        w.println("<p>");
        p.execute();
        w.println("</p>");
        return this;
    }

    public HtmlWriter h2(String text) {
        w.println(format("<h2>%s</h2>", escape(text)));
        return this;
    }

    public HtmlWriter h3(String text) {
        w.println(format("<h3>%s</h3>", escape(text)));
        return this;
    }

    public HtmlWriter h4(String text) {
        w.println(format("<h4>%s</h4>", escape(text)));
        return this;
    }

    private String escape(String text) {
        return text.replace("<", "&lt;").replace(">", "&gt;");
    }

    public HtmlWriter css(String cssFilename) {
        w.println(format("<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/css/%s\" media=\"screen\" />", cssFilename));
        return this;
    }

    public HtmlWriter script(String jsFilename) {
        w.println(format("<script src=\"/static/js/%s\"></script>", jsFilename));
        return this;
    }

    public HtmlWriter title(String text) {
        w.println(format("<title>%s</title>", text));
        return this;
    }
}
