package com.ind.tired.web.handler;

import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedRouteHandler implements RouteHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\" Engine\"");
        response.setContentType("text/html; charset=UTF-8");
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
    }
}
