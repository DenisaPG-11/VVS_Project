package com.serverweb.webserver.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {

    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper () {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    public static JsonNode parse (String jsonSrc) throws IOException {
        return  myObjectMapper.readTree(jsonSrc);
    }

    public static <A> A fromJson (JsonNode node, Class<A> objClass) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node,objClass);
    }

    public static JsonNode toJson (Object object) {
        return myObjectMapper.valueToTree(object);
    }

    public static String stringify (JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode, false);
    }

    public static String stringifyPretty (JsonNode jsonNode) throws JsonProcessingException {
        return generateJson(jsonNode, true);
    }

    private static String generateJson ( Object obj, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(pretty){
            objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }
        return objectWriter.writeValueAsString(obj);
    }
}
