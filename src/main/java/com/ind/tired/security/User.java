package com.ind.tired.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Joiner;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class User {
    public final Id id;
    public final String name;
    public final Set<Role> roles;
    public final boolean enabled;

    private User(Id id, String name, Set<Role> roles, boolean enabled) {
        this.id = id;
        this.name = name;
        this.roles = roles;
        this.enabled = enabled;
    }

    public String rolesString() {
        return Joiner.on(',').join(roles.stream().map(r -> r.name).collect(toSet()));
    }

    public static User of(Id id, String name, Set<Role> roles, boolean enabled) {
        return new User(id, name, roles, enabled);
    }

    public static User of(JsonNode node) {
        return of(JsonMiner.id(node, "id"), node);
    }

    public static User of(Id id, JsonNode node) {
        return of(
                id,
                string(node, "name"),
                Role.of(node.get("roles")),
                node.get("enabled").asBoolean());
    }

    public static User create(String name, Set<String> roles) {
        return of(Id.create(), name, roles.stream().map(Role::new).collect(toSet()), true);
    }

    public boolean hasOneOf(Set<Role> required) {
        return required.stream().anyMatch(roles::contains);
    }
}
