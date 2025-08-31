package automation.factories;

import automation.models.User;

public class UserDataFactory {

    private static String uniqueEmail(String prefix) {
        return prefix + System.currentTimeMillis() + "@example.com";
    }

    /** A valid active user */
    public static User validUser() {
        return new User("Test User", uniqueEmail("user"), "male", "active");
    }

    /** A valid inactive user */
    public static User inactiveUser() {
        return new User("Inactive User", uniqueEmail("inactive"), "male", "inactive");
    }

    /** Invalid user missing email (useful for 422 tests) */
    public static User invalidMissingEmail() {
        return new User("Invalid User", null, "female", "active");
    }
}
