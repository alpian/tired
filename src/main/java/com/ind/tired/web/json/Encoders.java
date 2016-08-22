package com.ind.tired.web.json;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ind.model.RateSet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Stream;

public class Encoders {
    public static void serialize(String name, Object o, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeFieldName(name);
        provider.findValueSerializer(o.getClass()).serialize(o, gen, provider);
    }

    public static <T> String encode(T t) throws IOException {
        try(StringWriter stringWriter = new StringWriter();
            JsonGenerator generator = generator(stringWriter))
        {
            mapper.writeValue(generator, t);

            return stringWriter.toString();
        }
    }

    public static <T> JsonNode asJsonNode(T value) {
        return mapper.valueToTree(value);
    }

    public static <T> ObjectNode asObjectNode(T value) {
        return (ObjectNode)asJsonNode(value);
    }

    private static JsonGenerator generator(StringWriter stringWriter) throws IOException {
        return new JsonFactory()
                .createGenerator(stringWriter)
                .useDefaultPrettyPrinter()
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        final SimpleModule module = new SimpleModule("Module", new Version(0, 1, 0, null, "com.ind.tired", "module"));
        Stream.of(
                OptionalSerializer.create(),
                DateTimeSerializer.create())
                .forEach(module::addSerializer);

        mapper.registerModule(module);
    }
}
