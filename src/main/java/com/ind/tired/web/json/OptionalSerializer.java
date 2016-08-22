package com.ind.tired.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Optional;

public class OptionalSerializer extends StdSerializer<Optional> {
    private OptionalSerializer() {
        super(Optional.class);
    }

    @Override
    public void serialize(Optional value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value.isPresent()) {
            final Object unwrapped = value.get();
            provider.findValueSerializer(unwrapped.getClass()).serialize(unwrapped, gen, provider);
        } else {
            gen.writeNull();
        }
    }

    public static OptionalSerializer create() {
        return new OptionalSerializer();
    }
}
