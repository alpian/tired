package com.ind.tired.web.json.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ind.tired.web.json.Encoders;

import java.util.Map;

public class JsonMaker {
    public static JsonNode make(Map<String, Object> properties) {
        final ObjectNode node = JsonNodeFactory.instance.objectNode();
        properties.forEach((name, value) -> node.set(name, Encoders.asJsonNode(value)));
        return node;
    }
}
