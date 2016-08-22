package com.ind.tired.web.handler;

import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ForbiddenRouteHandler implements RouteHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        final PrintWriter w = response.getWriter();
        w.println("<!DOCTYPE html>");
        w.println("<html>");
        w.println("<head><title> Server: Forbidden</title></meta></head>");
        w.println("<body>");
        w.println("<h1> Server: Forbidden</h1>");
        w.println("<p>Access denied.</p>");
        w.println("</body>");
        w.println("</html>");
    }
}
