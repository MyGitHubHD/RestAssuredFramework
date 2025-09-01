package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UsersGetTests extends BaseTest {

    @Test(groups = {"get","smoke","contract"})
    public void list_users_page1_has_items_and_matches_schema() {
        UsersService users = new UsersService(reqSpec);
        users.listUsers(1)
             .then()
             .statusCode(200)
             .time(lessThan(2000L))
             .header("Content-Type", startsWith("application/json"))
             .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/users_list_schema.json"))
             .body("size()", greaterThan(0));
    }

    @Test(groups = {"get","contract"})
    public void get_single_existing_user_matches_schema_or_404() {
        UsersService users = new UsersService(reqSpec);
        int status = users.getUser(1).then().extract().statusCode();
        if (status == 200) {
            users.getUser(1)
                 .then()
                 .statusCode(200)
                 .time(lessThan(2000L))
                 .header("Content-Type", startsWith("application/json"))
                 .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user_schema.json"))
                 .body("id", equalTo(1));
        } else {
            users.getUser(1).then().statusCode(404);
        }
    }

    @Test(groups = {"get"})
    public void get_nonexistent_user_returns_404() {
        UsersService users = new UsersService(reqSpec);
        users.getUser(9999999L)
             .then()
             .statusCode(404)
             .time(lessThan(2000L));
    }
}
