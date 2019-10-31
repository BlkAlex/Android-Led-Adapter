package com.blk.testcolorchooser.scenarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    public static <T> T getResponseStep(String response, Class<T> clazz) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response,clazz);
    }
}