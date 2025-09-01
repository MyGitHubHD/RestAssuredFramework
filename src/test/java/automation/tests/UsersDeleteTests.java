package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import automation.utils.UserDataFactory;
import automation.models.User;
import org.testng.annotations.Test;

public class UsersDeleteTests extends BaseTest {

    @Test(groups = {"delete","regression"})
    public void delete_existing_user_returns_204_then_get_404() {
        UsersService users = new UsersService(reqSpec);
        User req = UserDataFactory.validUser();
        long id = ((Number) users.createUser(req).then().statusCode(201).extract().path("id")).longValue();

        users.deleteUser(id).then().statusCode(204);
        users.getUser(id).then().statusCode(404);
    }

    @Test(groups = {"delete","negative"})
    public void delete_nonexistent_user_returns_404() {
        UsersService users = new UsersService(reqSpec);
        users.deleteUser(9999999L).then().statusCode(404);
    }
}
