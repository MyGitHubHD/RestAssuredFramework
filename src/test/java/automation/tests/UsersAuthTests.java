package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import automation.config.ConfigManager;
import automation.utils.UserDataFactory;
import automation.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UsersAuthTests extends BaseTest {

    @Test(groups = {"auth","security"})
    public void create_with_valid_token_succeeds_201() {
        UsersService users = new UsersService(reqSpec);
        users.createUser(UserDataFactory.validUser())
             .then()
             .statusCode(201)
             .time(lessThan(3000L))
             .header("Content-Type", containsString("application/json"));
    }

    @Test(groups = {"auth","security","negative"})
    public void create_with_missing_token_returns_401() {
        User u = UserDataFactory.validUser();
        given()
            .baseUri(ConfigManager.get("base.uri"))
            .basePath(ConfigManager.get("base.path"))
            .relaxedHTTPSValidation()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(u)
        .when()
            .post("/users")
        .then()
            .statusCode(401);
    }

    @Test(groups = {"auth","security","negative"})
    public void create_with_invalid_token_returns_401() {
        UsersService users = new UsersService(
            given().baseUri(ConfigManager.get("base.uri"))
                   .basePath(ConfigManager.get("base.path"))
                   .relaxedHTTPSValidation()
                   .contentType(ContentType.JSON)
                   .accept(ContentType.JSON)
                   .header("Authorization", "Bearer invalidtoken")
        );

        users.createUser(UserDataFactory.validUser())
             .then()
             .statusCode(401);
    }
}
