package com.belpost.apas.service.util;

import com.belpost.apas.model.common.Node;
import com.belpost.apas.model.common.NodeModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomObjectMapper {
    private final ObjectMapper objectMapper;

    public <T> T readFromFile(String file, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(file), clazz);
    }

    public <T> List<T> readListFromFile(String file, Class<T> clazz) throws IOException {
        JavaType type = objectMapper.getTypeFactory().
            constructCollectionType(List.class, clazz);
        return objectMapper.readValue(new File(file), type);
    }

    public <T extends NodeModel> Node<T> readNodeFromFile(String file, Class<T> clazz) throws IOException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaType type = objectMapper.getTypeFactory().
            constructParametricType(Node.class, clazz);
        return objectMapper.readValue(new File(file), type);
    }

}
