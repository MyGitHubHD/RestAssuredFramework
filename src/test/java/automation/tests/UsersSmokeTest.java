package automation.tests;

import automation.base.BaseTest;
import automation.core.GoRestClient;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UsersSmokeTest extends BaseTest {

    @Test
    public void list_users_page1_schema(){
        GoRestClient c = new GoRestClient(reqSpec);
        c.listUsers(1)
         .then().statusCode(200)
         .and().body("size()", greaterThan(0))
         .and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/users_list_schema.json"));
    }
}
