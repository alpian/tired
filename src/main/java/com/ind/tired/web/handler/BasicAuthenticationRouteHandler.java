package com.ind.tired.web.handler;


import com.google.common.base.Charsets;
import com.ind.data.DataAccess;
import com.ind.data.table.UserTable;
import com.ind.security.Role;
import com.ind.security.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

public class BasicAuthenticationRouteHandler implements RouteHandler {
    private final UserTable userTable;
    private final Set<Role> restrictedToRoles;
    private final RouteHandler permitted;
    private final RouteHandler unauthorized;
    private final RouteHandler forbidden;

    public BasicAuthenticationRouteHandler(
            UserTable userTable,
            Set<Role> restrictedToRoles, RouteHandler permitted,
            RouteHandler unauthorized,
            ForbiddenRouteHandler forbidden) {
        this.userTable = userTable;
        this.restrictedToRoles = restrictedToRoles;
        this.permitted = permitted;
        this.unauthorized = unauthorized;
        this.forbidden = forbidden;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (restrictedToRoles.isEmpty()) {
            permitted.handle(request, response);
            return;
        }
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic")) {
            unauthorized.handle(request, response);
            return;
        }

        // e.g. Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
        final String usernamePassword = decode(authorizationHeader);
        final String[] split = usernamePassword.split(":", 2);
        final String username = split[0];
        final String password = split[1];

        if (userTable.checkPassword(username, password)) {
            final Optional<User> user = userTable.get(username);
            if (user.filter(u -> u.hasOneOf(restrictedToRoles)).isPresent()) {
                permitted.handle(request, response);
            } else {
                forbidden.handle(request, response);
            }
        } else {
            unauthorized.handle(request, response);
        }
    }

    private String decode(String s) {
        return new String(Base64.getDecoder().decode(s.substring(6)), Charsets.UTF_8);
    }

    public static RouteHandler forRole(DataAccess dataAccess, Set<Role> permittedRoles, RouteHandler handler) {
        return new BasicAuthenticationRouteHandler(
                new UserTable(dataAccess),
                permittedRoles,
                handler,
                new UnauthorizedRouteHandler(),
                new ForbiddenRouteHandler());
    }
}
