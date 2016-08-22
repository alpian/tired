package com.ind.tired.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.ind.tired.util.CheckedCallable;
import com.ind.tired.web.json.Encoders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WebResult {
    private final ContentType contentType;
    private final int status;
    private final CheckedCallable<PrintWriter, IOException> contentWriter;

    private WebResult(ContentType contentType, int status) {
        this(contentType, status, Optional.empty());
    }

    private WebResult(ContentType contentType, int status, String content) {
        this(contentType, status, Optional.ofNullable(content));
    }

    private WebResult(ContentType contentType, int status, Optional<String> content) {
        this(contentType, status, (w) -> {
            if (content.isPresent()) {
                w.print(content.get());
            }
        });
    }

    public WebResult(ContentType contentType, int status, CheckedCallable<PrintWriter, IOException> contentWriter) {
        this.contentType = contentType;
        this.status = status;
        this.contentWriter = contentWriter;
    }

    public static WebResult okJsonList(Stream<JsonNode> rates) {
        return okJsonList(rates.collect(toList()));
    }

    public void apply(HttpServletResponse response) throws IOException {
        response.setContentType(contentType.toString());
        response.setStatus(status);
        contentWriter.apply(response.getWriter());
    }

    public static WebResult error(String reason) {
        logger.error(reason);
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_INTERNAL_SERVER_ERROR, reason);
    }

    public static WebResult error(Throwable e) {
        logger.error(e.getMessage(), e);
        final StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_INTERNAL_SERVER_ERROR, out.toString());
    }

    public static WebResult unprocessable(String reason) {
        logger.error(reason);
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_UNPROCESSABLE_ENTITY, reason);
    }

    public static WebResult okJson(String content) {
        return new WebResult(ContentType.APPLICATION_JSON, HttpStatus.SC_OK, content);
    }

    public static WebResult okJsonObject(Object o) {
        return jsonObject(HttpStatus.SC_OK, o);
    }

    public static <T> WebResult okJsonList(List<T> items) {
        if (items.isEmpty()) {
            return notFound();
        }

        return okJsonObject(items);
    }

    public static <T> WebResult okJsonObjectOrNotFound(Optional<T> optional) {
        return optional.map(WebResult::okJsonObject).orElse(notFound());
    }

    public static WebResult createdJsonObject(Object o) {
        return jsonObject(HttpStatus.SC_CREATED, o);
    }

    public static WebResult jsonObject(int status, Object o) {
        return new WebResult(ContentType.APPLICATION_JSON, status, (w) -> w.write(Encoders.encode(o)));
    }

    public static WebResult ok() {
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_OK);
    }

    public static WebResult noContent() {
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_NO_CONTENT);
    }

    private static WebResult created() {
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_CREATED);
    }

    public static WebResult okHtml(CheckedCallable<PrintWriter, IOException> f) {
        return new WebResult(ContentType.TEXT_HTML, HttpStatus.SC_OK, f);
    }

    public static WebResult notFound() {
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_NOT_FOUND);
    }

    private static final Logger logger = LoggerFactory.getLogger(WebResult.class);

    public static WebResult badRequest() {
        return new WebResult(ContentType.TEXT_PLAIN, HttpStatus.SC_BAD_REQUEST);
    }
}
