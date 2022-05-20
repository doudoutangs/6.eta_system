package com.eta.core.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * java 对象转换为 json 字符串
     *
     * @param obj
     *            对象
     * @return json
     */
    public static String toJSON(Object obj) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String result = writer.toString();
        return (null == result) ? "" : result.replaceAll("null", "\"\"");
    }

    /**
     * json字符串转换为对象
     *
     * @param <T>
     * @param json
     *            json字符串
     * @param clazz
     *            要转换对象的class
     * @return 对象
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {

        try {
            return mapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> List<T>   fromJsonList(String json,Class<T> clazz){
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
