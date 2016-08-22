package com.ind.tired.web.handler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundRouteHandler implements RouteHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head><title> Server: Not found</title></meta></head>");
        response.getWriter().println("<body><h1> Server: Not found</h1><p>No such route</p></body>");
        response.getWriter().println("</html>");
    }
}
