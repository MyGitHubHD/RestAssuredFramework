package automation.utils;

import java.util.HashMap;
import java.util.Map;

public class UserDataFactory {

    public static Map<String,Object> newUser(String namePrefix, String gender, String status){
        Map<String,Object> body = new HashMap<>();
        body.put("name", namePrefix + System.currentTimeMillis());
        body.put("email", "user" + System.currentTimeMillis() + "@example.com");
        body.put("gender", gender);
        body.put("status", status);
        return body;
    }

    public static Map<String,Object> updateUser(String newName, String newStatus){
        Map<String,Object> patch = new HashMap<>();
        patch.put("name", newName);
        patch.put("status", newStatus);
        return patch;
    }
}
