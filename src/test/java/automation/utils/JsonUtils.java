package automation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private JsonUtils(){}

    public static List<Map<String,String>> readListOfMaps(String classpathResource){
        try (InputStream is = JsonUtils.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) throw new IllegalArgumentException("Resource not found: " + classpathResource);
            return MAPPER.readValue(is, new TypeReference<List<Map<String,String>>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
