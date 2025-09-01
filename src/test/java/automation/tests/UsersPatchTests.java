package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import automation.utils.UserDataFactory;
import automation.models.User;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class UsersPatchTests extends BaseTest {

    @Test(groups = {"patch","regression"})
    public void patch_existing_user_updates_fields_and_returns_200() {
        UsersService users = new UsersService(reqSpec);
        User req = UserDataFactory.validUser();
        long id = ((Number) users.createUser(req).then().statusCode(201).extract().path("id")).longValue();

        Map<String, Object> patch = new HashMap<String, Object>();
        patch.put("name", "Patched Name");
        patch.put("status", "inactive");

        users.updateUserPatch(id, patch)
             .then()
             .statusCode(200)
             .time(lessThan(2000L))
             .header("Content-Type", containsString("application/json"))
             .body("id", equalTo((int) id))
             .body("name", equalTo("Patched Name"))
             .body("status", equalTo("inactive"));

        users.deleteUser(id).then().statusCode(204);
    }

    @Test(groups = {"patch","negative"})
    public void patch_nonexistent_user_returns_404() {
        UsersService users = new UsersService(reqSpec);
        Map<String, Object> patch = new HashMap<String, Object>();
        patch.put("status","inactive");

        users.updateUserPatch(9999999L, patch)
             .then()
             .statusCode(404);
    }
}
