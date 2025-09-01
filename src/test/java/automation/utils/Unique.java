package automation.utils;

import java.util.UUID;

public final class Unique {
    private Unique(){}

    public static String email(String prefix){
        return prefix + System.currentTimeMillis() + "@example.com";
    }

    public static String name(String base){
        return base + "_" + System.currentTimeMillis();
    }

    public static String uuid(){
        return UUID.randomUUID().toString();
    }
}
