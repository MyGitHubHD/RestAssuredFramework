package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import automation.utils.UserDataFactory;
import automation.models.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UsersPostTests extends BaseTest {

    @Test(groups = {"post","regression","contract"})
    public void create_valid_user_returns_201_and_matches_schema() {
        UsersService users = new UsersService(reqSpec);
        User req = UserDataFactory.validUser();

        users.createUser(req)
             .then()
             .statusCode(201)
             .time(lessThan(2000L))
             .header("Content-Type", containsString("application/json"))
             .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user_schema.json"))
             .body("name", equalTo(req.getName()))
             .body("email", equalTo(req.getEmail()))
             .body("status", equalTo(req.getStatus()));
    }

    @Test(groups = {"post","negative"})
    public void create_user_duplicate_email_returns_422() {
        UsersService users = new UsersService(reqSpec);
        User first = UserDataFactory.validUser();
        long id = ((Number) users.createUser(first).then().statusCode(201).extract().path("id")).longValue();

        User dup = new User(first.getName(), first.getEmail(), "male", "active");
        users.createUser(dup)
             .then()
             .statusCode(422)
             .body("field", hasItem("email"));

        users.deleteUser(id).then().statusCode(204);
    }

    @Test(groups = {"post","negative"})
    public void create_user_missing_email_returns_422() {
        UsersService users = new UsersService(reqSpec);
        User bad = new User("Missing Email", null, "female", "active");

        users.createUser(bad)
             .then()
             .statusCode(422)
             .body("field", hasItem("email"));
    }
}
