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
}
