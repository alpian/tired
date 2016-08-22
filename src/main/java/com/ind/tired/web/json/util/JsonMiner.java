package com.ind.tired.web.json.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

public class JsonMiner {
    public static JsonNode read(String content) throws IOException {
        return new ObjectMapper().readTree(content);
    }

    public static List<JsonNode> array(JsonNode node) {
        return newArrayList(node.elements());
    }

    public static JsonNode nth(JsonNode node, int n) {
        return newArrayList(node.elements()).get(n);
    }

    public static String string(JsonNode node, String key) {
        return node.get(key).asText();
    }

    public static JsonNode node(JsonNode node, String key) {
        return node.get(key);
    }

    public static JsonNodeType typeOf(JsonNode node, String key) {
        return node(node, key).getNodeType();
    }

    public static int integer(JsonNode node, String key) {
        return node.get(key).asInt();
    }

    public static boolean bool(JsonNode node, String key) {
        return node.get(key).asBoolean();
    }

    public static List<JsonNode> list(JsonNode node, String key) {
        return newArrayList(node.get(key).elements());
    }

    public static Optional<JsonNode> find(List<JsonNode> nodes, Predicate<JsonNode> predicate) {
        return nodes.stream().filter(predicate).findFirst();
    }
}
