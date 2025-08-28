package com.mock.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class ObjectMapperUtils {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String mapToString(Map<String, Object> data) throws JsonProcessingException {
        return mapper.writeValueAsString(data);
    }
}
