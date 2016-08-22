package com.ind.tired.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ind.tired.util.Try;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.stream.Stream;

public class ReflectionBasedSerializer<T> extends StdSerializer<T> {
    private final Class<T> clazz;

    private ReflectionBasedSerializer(Class<T> clazz) {
        super(clazz);
        this.clazz = clazz;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        final Optional<Try<Void>> result = Stream.of(clazz.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()))
                .map(f -> Try.doing(() ->
                    Encoders.serialize(f.getName(), extractInstanceValue(value, f), gen, provider)
                ))
                .filter(Try::isFailure)
                .findFirst();

        if (result.isPresent()) {
            result.get().fail();
        }

        gen.writeEndObject();
    }

    private Object extractInstanceValue(T value, Field f) {
        try {
            return f.get(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ReflectionBasedSerializer<T> create(Class<T> clazz) {
        return new ReflectionBasedSerializer<>(clazz);
    }
}
