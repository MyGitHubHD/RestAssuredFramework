package automation.utils;

import automation.models.User;

import java.util.Map;

public final class UserDataFactory {
    private UserDataFactory(){}

    public static User validUser(){
        return new User("Test User", Unique.email("user."), "male", "active");
    }

    public static User inactiveUser(){
        return new User("Inactive User", Unique.email("inactive."), "male", "inactive");
    }

    public static User invalidMissingEmail(){
        return new User("Missing Email", null, "female", "active");
    }

    public static User fromMap(Map<String, String> m){
        String name   = m.getOrDefault("name", "Gen User");
        String email  = m.getOrDefault("email", Unique.email("user."));
        String gender = m.getOrDefault("gender", "male");
        String status = m.getOrDefault("status", "active");
        return new User(name, email, gender, status);
    }
}
