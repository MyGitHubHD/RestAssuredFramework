package automation.tests;

import automation.base.BaseTest;
import automation.core.UsersService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class UsersCrudTests extends BaseTest {

    private static String uniqueEmail(String prefix){
        return prefix + System.currentTimeMillis() + "@example.com";
    }

    @Test
    public void create_get_update_delete_user_e2e(){
        UsersService users = new UsersService(reqSpec);

        Map<String,Object> body = new HashMap<String,Object>();
        body.put("name",   "Assignment User");
        body.put("email",  uniqueEmail("abhishek."));
        body.put("gender", "male");
        body.put("status", "active");

        Response createResp = users.createUser(body);
        createResp.then().statusCode(201)
                .body("name", equalTo("Assignment User"))
                .body("status", equalTo("active"))
                .body("email", not(isEmptyOrNullString()));

        long id = ((Number)createResp.then().extract().path("id")).longValue();

        users.getUser(id).then().statusCode(200)
                .body("id", equalTo((int)id))
                .body("email", equalTo((String)body.get("email")));

        Map<String,Object> patch = new HashMap<String,Object>();
        patch.put("name", "Assignment User Updated");
        patch.put("status", "inactive");

        users.updateUserPatch(id, patch).then().statusCode(200)
                .body("name", equalTo("Assignment User Updated"))
                .body("status", equalTo("inactive"));

        users.deleteUser(id).then().statusCode(204);

        users.getUser(id).then().statusCode(404);
    }

    @Test
    public void missing_token_returns_401_on_create(){
        // Build a call WITHOUT using reqSpec so no Authorization header is sent
        Map<String,Object> body = new HashMap<String,Object>();
        body.put("name","NoAuth");
        body.put("email", uniqueEmail("noauth."));
        body.put("gender","female");
        body.put("status","active");

        given()
            .baseUri(automation.config.ConfigManager.get("base.uri"))
            .basePath(automation.config.ConfigManager.get("base.path"))
            .relaxedHTTPSValidation()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(body)
        .when()
            .post(automation.core.Routes.USERS)
        .then()
            .statusCode(401);
    }

    @Test
    public void duplicate_email_returns_422(){
        UsersService users = new UsersService(reqSpec);
        String email = uniqueEmail("dup.");

        Map<String,Object> a = new HashMap<String,Object>();
        a.put("name","User A"); a.put("email", email); a.put("gender","female"); a.put("status","active");
        long idA = ((Number)users.createUser(a).then().statusCode(201).extract().path("id")).longValue();

        Map<String,Object> b = new HashMap<String,Object>();
        b.put("name","User B"); b.put("email", email); b.put("gender","female"); b.put("status","active");

        users.createUser(b).then().statusCode(422)
                .body("field", hasItem("email"));

        users.deleteUser(idA).then().statusCode(204);
    }
}
