package com.belpost.apas.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
@RequiredArgsConstructor
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public <T> T readFromFile (String file, Class<T> clazz) throws IOException {
        return objectMapper.readValue(new File(file), clazz);
    }

    public <T> List<T> readListFromFile (String file, Class<T> clazz) throws IOException {
        JavaType type = objectMapper.getTypeFactory().
            constructCollectionType(List.class, clazz);
        return objectMapper.readValue(new File(file), type);
    }

    public String getJsonRequest(Object request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    public <T> T getResponse(MvcResult result, Class<T> clazz)
        throws UnsupportedEncodingException, JsonProcessingException {
        var jsonString = result.getResponse().getContentAsString();
        return objectMapper.readValue(jsonString, clazz);
    }

    public <T> List<T> getResponseToList(MvcResult result, TypeReference<List<T>> type)
        throws UnsupportedEncodingException, JsonProcessingException {
        var jsonString = result.getResponse().getContentAsString();

        return objectMapper.readValue(jsonString, type);
    }

    public <T> List<T> getResponseFromPage(MvcResult result, TypeReference<List<T>> type)
        throws UnsupportedEncodingException, JsonProcessingException, JSONException {
        var jsonString = result.getResponse().getContentAsString();
        var content = new JSONObject(jsonString).getString("content");
        return objectMapper.readValue(content, type);
    }
}
