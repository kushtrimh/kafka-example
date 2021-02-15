package com.kushtrimh.kafkaexampleproducer.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Kushtrim Hajrizi
 */
public class JSONConverter {

    private static final Logger log = LoggerFactory.getLogger(JSONConverter.class);

    private ObjectMapper mapper;

    public JSONConverter(PropertyNamingStrategy namingStrategy) {
        mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(namingStrategy);
    }

    public <T> String toJSON(Class<T> entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error("Could not convert entity to JSON", e);
        }
        return "";
    }

    public <T> Optional<T> fromJSON(String content, Class<T> cls) {
        try {
            return Optional.of(mapper.readValue(content, cls));
        } catch (JsonProcessingException e) {
            log.error("Could not convert to JSON");
        }
        return Optional.empty();
    }
}
