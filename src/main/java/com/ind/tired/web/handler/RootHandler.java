package com.ind.tired.web.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RootHandler extends AbstractHandler {
    private final String version;
    private final Routes routes;

    public RootHandler(String version, Routes routes) {
        this.version = version;
        this.routes = routes;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String requestId = UUID.randomUUID().toString();

        response.setHeader("X-Server-Version", version);
        response.setHeader("X-Server-Request-ID", requestId);
        logger.info(String.format("[%s]: %s to %s requested", requestId, request.getMethod(), request.getRequestURI()));

        try {
            routes.route(request, response);
            logger.info(String.format("[%s] Response content type %s with status code %s sent", requestId, response.getContentType(), response.getStatus()));
            baseRequest.setHandled(true);
        } catch (Throwable ex) {
            logger.error(ex.getMessage(), ex);
            new ServerErrorRouteHandler(ex).handle(request, response);
            baseRequest.setHandled(true);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(RootHandler.class);
}
