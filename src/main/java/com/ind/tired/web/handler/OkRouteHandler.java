package com.ind.tired.web.handler;


import org.apache.http.entity.ContentType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OkRouteHandler implements RouteHandler {

    public OkRouteHandler() {
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(ContentType.TEXT_PLAIN.toString());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static OkRouteHandler ok() {
        return new OkRouteHandler();
    }
}
