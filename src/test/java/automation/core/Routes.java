package automation.core;

public final class Routes {
    private Routes(){}
    public static final String USERS = "/users";
    public static String user(long id){ return "/users/" + id; }
}
