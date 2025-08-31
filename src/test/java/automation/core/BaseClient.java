package automation.core;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/** Reusable CRUD helpers that service classes can use. */
public abstract class BaseClient {
    protected final RequestSpecification baseSpec;

    protected BaseClient(RequestSpecification baseSpec) {
        this.baseSpec = baseSpec;
    }

    protected Response get(String path) {
        return RequestBuilder.prepare(baseSpec, null, null, null).when().get(path);
    }

    protected Response get(String path, Map<String, ?> query, Map<String, String> headers) {
        return RequestBuilder.prepare(baseSpec, query, headers, null).when().get(path);
    }

    protected Response post(String path, Object body) {
        return RequestBuilder.prepare(baseSpec, null, null, body).when().post(path);
    }

    protected Response patch(String path, Object body) {
        return RequestBuilder.prepare(baseSpec, null, null, body).when().patch(path);
    }

    protected Response delete(String path) {
        return RequestBuilder.prepare(baseSpec, null, null, null).when().delete(path);
    }
}
