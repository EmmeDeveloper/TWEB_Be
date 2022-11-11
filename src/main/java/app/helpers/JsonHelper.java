package app.helpers;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.sql.Statement;
import java.util.zip.GZIPOutputStream;

public class JsonHelper {

//    private final static GsonBuilder builder = GetBuilder();
//    static JsonHelper GetBuilder() {
//        var build = new GsonBuilder();
//        build.setPrettyPrinting();
//        build.setFieldNamingStrategy(f -> f.getName().toLowerCase());
//        return build;
//    }



    public static <T> T FromJsonRequest(HttpServletRequest request, Class TClass) throws JsonProcessingException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { e.printStackTrace(); }
        return FromJson(jb.toString(), TClass);
    }

    public static <T> T FromJson(String json, Class TClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        return (T)objectMapper.readValue(json, TClass);
    }
    public static <T> String ToJson(T value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        return objectMapper.writeValueAsString(value);
//        Gson gson = builder.create();
//        return gson.toJson(value);
    }

}
