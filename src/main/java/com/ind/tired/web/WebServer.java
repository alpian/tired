package com.ind.tired.web;


import com.ind.tired.web.handler.RootHandler;
import com.typesafe.config.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private final String version;
    private final Config config;
    private final Uri root;
    private final Routes routes;
    private final Server server = new Server();

    public WebServer(String version, Config config, Uri root, Routes routes) {
        this.version = version;
        this.config = config;
        this.root = root;
        this.routes = routes;
    }

    public void start() {
        try {
            final ServerConnector httpConnector = new ServerConnector(server);

            httpConnector.setHost(root.host);
            httpConnector.setPort(root.port);
            httpConnector.setIdleTimeout(config.getInt("web.idle-timeout"));
            server.addConnector(httpConnector);

            final HandlerList handlers = new HandlerList();

            handlers.addHandler(inContext("/static", newResourcesHandler()));
            handlers.addHandler(inContext("/", new RootHandler(version, routes)));

            server.setHandler(handlers);
            server.start();

            log.info("Web Server started on http://" + root.host + ":" + root.port);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    private ContextHandler inContext(String serveAt, AbstractHandler handler) {
        final ContextHandler context = new ContextHandler(serveAt);
        context.setHandler(handler);
        return context;
    }

    private ResourceHandler newResourcesHandler() throws MalformedURLException {
        final ResourceHandler resources = new ResourceHandler();
        final Resource location =
                config.getBoolean("web.static.resources.embedded") ?
                          Resource.newClassPathResource("web", false, false)
                        : Resource.newResource("src/main/resources/web", false);
        resources.setBaseResource(location);
        resources.setCacheControl("no-store");
        return resources;
    }

    public void stop() throws Exception {
        server.stop();
    }
}
