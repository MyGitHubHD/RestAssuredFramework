package automation.core;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/** Service class for /users endpoints (replaces GoRestClient). */
public class UsersService extends BaseClient {

    public UsersService(RequestSpecification baseSpec) {
        super(baseSpec);
    }

    public Response listUsers(int page) {
        return get(Routes.USERS + "?page=" + page);
    }

    public Response getUser(long id) {
        return get(Routes.user(id));
    }

    public Response createUser(Object body) {
        return post(Routes.USERS, body);
    }

    public Response updateUserPatch(long id, Object body) {
        return patch(Routes.user(id), body);
    }

    public Response deleteUser(long id) {
        return delete(Routes.user(id));
    }
}
