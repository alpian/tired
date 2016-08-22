package com.ind.tired.web;

import com.ind.security.Role;
import com.ind.tired.web.documentation.Describable;
import com.ind.tired.web.handler.RouteHandler;
import com.ind.tired.web.html.HtmlWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Function;

public class Route {
    public final Set<Role> roles;
    private final Signature signature;
    private final Function<WebRequest, RouteHandler> handlerFactory;
    private final Describable description;

    public Route(Set<Role> roles, Signature signature, Function<WebRequest, RouteHandler> handlerFactory, Describable description) {
        this.roles = roles;
        this.signature = signature;
        this.handlerFactory = handlerFactory;
        this.description = description;
    }

    public RouteHandler route(Method method, String uri, HttpServletRequest request) {
        final WebRequest webRequest = new WebRequest(method, uri, signature.parameters(uri), request);
        return handlerFactory.apply(webRequest);
    }

    @Override
    public String toString() {
        return signature.toString();
    }

    public void describeTo(PrintWriter writer) {
        signature.describeTo(writer);
        description.describeTo(new HtmlWriter(writer));
    }

    public boolean matches(Method method, String uri, String queryString) {
        return signature.matches(method, uri, queryString);
    }
}
