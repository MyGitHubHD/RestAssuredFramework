package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UsersSmokeTest extends BaseTest {

    @Test
    public void list_users_page1_schema(){
        UsersService users = new UsersService(reqSpec);
        users.listUsers(1)
             .then()
             .statusCode(200)
             .body("size()", greaterThan(0))
             .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/users_list_schema.json"));
    }
}
