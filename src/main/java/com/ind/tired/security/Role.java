package com.ind.tired.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class Role {
    public final String name;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name.equals(role.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Set<Role> of(JsonNode roles) {
        return array(roles).stream().map(j -> j.get("name").asText()).map(Role::new).collect(toSet());
    }

    public static Set<Role> adminAnd(Role... roles) {
        return ImmutableSet.<Role>builder().add(Role.ADMIN).add(roles).build();
    }

    public static Set<Role> none() {
        return ImmutableSet.of();
    }
}
