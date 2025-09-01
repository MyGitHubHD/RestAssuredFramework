package automation.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class CsvUtils {
    private CsvUtils(){}

    public static List<Map<String,String>> read(String classpathResource) {
        List<Map<String,String>> rows = new ArrayList<Map<String,String>>();
        try (InputStream is = CsvUtils.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (is == null) throw new IllegalArgumentException("Resource not found: " + classpathResource);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String headerLine = br.readLine();
            if (headerLine == null) return rows;
            String[] headers = split(headerLine);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] values = split(line);
                Map<String,String> map = new HashMap<String,String>();
                for (int i=0; i<headers.length && i<values.length; i++) {
                    map.put(headers[i].trim(), values[i].trim());
                }
                rows.add(map);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    private static String[] split(String line){
        return line.split("\s*,\s*");
    }
}
