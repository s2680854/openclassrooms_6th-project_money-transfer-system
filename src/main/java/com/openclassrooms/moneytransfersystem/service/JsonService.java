package com.openclassrooms.moneytransfersystem.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JsonService {

    private static Logger logger = LoggerFactory.getLogger(JsonService.class);

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        defaultObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return defaultObjectMapper;
    }

    public static ObjectMapper mapper = getDefaultObjectMapper();

    public static String toJson(Set<String> list, boolean indented) throws JsonProcessingException {

        logger.debug("[toJson] list: " + list);
        ObjectWriter objectWriter = mapper.writer();
        if (indented) {objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);}
        String jsonString = objectWriter.writeValueAsString(list);

        return jsonString;
    }

    public static Set<String> toSetOfString(String jsonString) throws JsonProcessingException {

        logger.debug("[toSetOfString] jsonString: " + jsonString);
        Set<String> list = mapper.readValue(jsonString, Set.class);

        return list;
    }
}