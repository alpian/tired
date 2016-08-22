package com.ind.tired.web.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RouteHandler {
    void handle(HttpServletRequest request, HttpServletResponse response) throws IOException;

    interface Factory {
        RouteHandler create();
    }
}
