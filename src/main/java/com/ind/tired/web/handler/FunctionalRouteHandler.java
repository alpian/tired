package com.ind.tired.web.handler;


import com.ind.tired.web.WebRequest;
import com.ind.tired.web.WebResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

public class FunctionalRouteHandler implements RouteHandler {
    private final WebRequest webRequest;
    private final Function<WebRequest, WebResult> f;

    private FunctionalRouteHandler(WebRequest webRequest, Function<WebRequest, WebResult> f) {
        this.webRequest = webRequest;
        this.f = f;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        f.apply(webRequest).apply(response);
    }

    public static Function<WebRequest, RouteHandler> call(Function<WebRequest, WebResult> f) {
        return w -> new FunctionalRouteHandler(w, f);
    }
}
