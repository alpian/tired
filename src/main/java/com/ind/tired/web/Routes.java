package com.ind.tired.web;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.ind.data.DataAccess;
import com.ind.security.Role;
import com.ind.tired.web.documentation.Describable;
import com.ind.tired.web.documentation.RouteDescription;
import com.ind.tired.web.handler.BasicAuthenticationRouteHandler;
import com.ind.tired.web.handler.NotFoundRouteHandler;
import com.ind.tired.web.handler.RouteHandler;
import com.ind.tired.web.html.HtmlWriter;
import com.ind.tired.web.handler.FunctionalRouteHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Routes {
    private final List<Route> routes = Lists.newArrayList();
    private final DataAccess dataAccess;

    public Routes(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public static Builder routing(DataAccess dataAccess) {
        return new Builder(dataAccess);
    }

    public void route(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final Method method = Method.valueOf(request.getMethod());
        final String uri = request.getRequestURI();
        routes.stream()
                .filter(r -> r.matches(method, uri, request.getQueryString()))
                .findFirst()
                .map(r -> BasicAuthenticationRouteHandler.forRole(dataAccess, r.roles, r.route(method, uri, request)))
                .orElse(new NotFoundRouteHandler())
                .handle(request, response);
    }

    public void describeTo(PrintWriter w) {
        final HtmlWriter h = new HtmlWriter(w);
        w.println("<!DOCTYPE html>");
        w.println("<html>");
        w.println("<head>");

        h.title("Engine Routes")
         .css("prism.css")
         .css("main.css");

        w.println("</head>");
        w.println("<body>");

        h.h1("Defined routes")
         .p("Please note that URI components prepended with a : (colon) indicate parameters that must be replaced with valid identifiers")
         .ul("routes", () -> routes.stream().forEach(r ->
                h.li("route", () -> r.describeTo(w))));

        h.script("prism.js");

        w.println("</body>");
        w.println("<html>");
    }

    public static class Builder {
        private final Routes routes;

        public Builder(DataAccess dataAccess) {
            routes = new Routes(dataAccess);
        }

        public Builder post(String uri, RouteHandler handler) {
            return post(uri, (w) -> handler);
        }

        public Builder post(String uri, Function<WebRequest, RouteHandler> handler) {
            return include(Method.POST, uri, handler);
        }

        public Builder post(String uri, Function<WebRequest, RouteHandler> handler, Describable description) {
            return include(Method.POST, uri, handler, description);
        }

        public Builder post(String uri, Set<Role> roles, Function<WebRequest, RouteHandler> handler, Describable description) {
            return include(Method.POST, uri, roles, handler, description);
        }

        public Builder put(String uri, RouteHandler handler) {
            return include(Method.PUT, uri, (w) -> handler);
        }

        public Builder put(String uri, Function<WebRequest, WebResult> handler) {
            return include(Method.PUT, uri, FunctionalRouteHandler.call(handler));
        }

        public Builder put(String uri, Function<WebRequest, WebResult> handler, Describable description) {
            return include(Method.PUT, uri, FunctionalRouteHandler.call(handler), description);
        }

        public Builder get(String uri, Function<WebRequest, WebResult> handler) {
            return include(Method.GET, uri, FunctionalRouteHandler.call(handler));
        }

        public Builder get(String uri, Set<Role> roles, Function<WebRequest, WebResult> handler) {
            return include(Method.GET, uri, roles, FunctionalRouteHandler.call(handler));
        }

        public Builder get(String uri, Function<WebRequest, WebResult> handler, Describable description) {
            return include(Method.GET, uri, FunctionalRouteHandler.call(handler), description);
        }

        public Builder get(String uri, Set<Role> roles, Function<WebRequest, WebResult> handler, Describable description) {
            return include(Method.GET, uri, roles, FunctionalRouteHandler.call(handler), description);
        }

        public Builder delete(String uri, Function<WebRequest, WebResult> handler) {
            return delete(uri, handler, RouteDescription.none());
        }

        public Builder delete(String uri, Function<WebRequest, WebResult> handler, Describable description) {
            return include(Method.DELETE, uri, FunctionalRouteHandler.call(handler), description);
        }

        private Builder include(Method method, String uri, Function<WebRequest, RouteHandler> handler) {
            return include(method, uri, handler, RouteDescription.none());
        }

        private Builder include(Method method, String uri, Set<Role> roles, Function<WebRequest, RouteHandler> handler) {
            return include(method, uri, roles, handler, RouteDescription.none());
        }

        private Builder include(Method method, String uri, Function<WebRequest, RouteHandler> handler, Describable description) {
            routes.include(Signature.of(method, uri), handler, description);
            return this;
        }

        private Builder include(Method method, String uri, Set<Role> roles, Function<WebRequest, RouteHandler> handler, Describable description) {
            routes.include(Signature.of(method, uri), roles, handler, description);
            return this;
        }

        public Routes finish() {
            return routes;
        }
    }

    public void include(Signature signature, Function<WebRequest, RouteHandler> handler, Describable description) {
        include(signature, ImmutableSet.of(Role.ADMIN), handler, description);
    }

    public void include(Signature signature, Set<Role> roles, Function<WebRequest, RouteHandler> handler, Describable description) {
        routes.add(new Route(roles, signature, handler, description));
    }
}
