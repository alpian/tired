package com.ind.tired.web.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerErrorRouteHandler implements RouteHandler {
    private final Throwable ex;

    public ServerErrorRouteHandler(Throwable ex) {
        this.ex = ex;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head><title> Server: Server error</title></meta></head>");
        response.getWriter().println("<body><h1> Server: " + ex.getMessage() + "</h1><pre>");
        ex.printStackTrace(response.getWriter());
        response.getWriter().println("</pre>");
        response.getWriter().println("</html>");
    }
}
