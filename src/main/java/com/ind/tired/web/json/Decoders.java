package com.ind.tired.web.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.io.Reader;


public class Decoders {
    private static DateTimeFormatter format = ISODateTimeFormat.dateTime();

    public static DateTime toDateTime(String s) {
        return format.parseDateTime(s);
    }

    public static JsonNode read(Reader reader) throws IOException {
        return mapper.readTree(reader);
    }

    public static String rawJson(JsonNode node) throws JsonProcessingException {
        return mapper.writeValueAsString(node);
    }

    public static <T> T decode(Class<T> clazz, JsonNode node) throws IOException {
        return mapper.readerFor(clazz).readValue(node);
    }

    private static ObjectMapper mapper = new ObjectMapper();
}
