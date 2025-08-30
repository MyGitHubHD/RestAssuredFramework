package automation.core;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GoRestClient {
    private final RequestSpecification spec;
    public GoRestClient(RequestSpecification spec){ this.spec = spec; }

    public Response listUsers(int page){
        return given().spec(spec).queryParam("page", page).when().get(Routes.USERS);
    }

    public Response getUser(long id){
        return given().spec(spec).when().get(Routes.user(id));
    }

    public Response createUser(Object body){
        return given().spec(spec).body(body).when().post(Routes.USERS);
    }

    public Response createUserWithoutAuth(Object body){
        return given().spec(spec).header("Authorization","").body(body).when().post(Routes.USERS);
    }

    public Response updateUserPatch(long id, Object body){
        return given().spec(spec).body(body).when().patch(Routes.user(id));
    }

    public Response deleteUser(long id){
        return given().spec(spec).when().delete(Routes.user(id));
    }
}
