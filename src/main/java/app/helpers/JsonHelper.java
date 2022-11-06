package app.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.var;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.sql.Statement;
import java.util.zip.GZIPOutputStream;

public class JsonHelper {

    private final static GsonBuilder builder = GetBuilder();
    static GsonBuilder GetBuilder() {
        var build = new GsonBuilder();
        build.setPrettyPrinting();
        build.setFieldNamingStrategy(f -> f.getName().toLowerCase());
        return build;
    }

    public static <T> T FromJsonRequest(HttpServletRequest request, Class TClass) {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { e.printStackTrace(); }
        return FromJson(jb.toString(), TClass);
    }

    public static <T> T FromJson(String json, Class TClass) {
        Gson gson = builder.create();
        return (T)gson.fromJson(json, TClass);
    }
    public static <T> String ToJson(T value) {
        Gson gson = builder.create();
        return gson.toJson(value);
    }

}
