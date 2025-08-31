package automation.core;

import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;

/** Central place to assemble RestAssured requests with headers/query/body. */
public final class RequestBuilder {
    private RequestBuilder(){}

    public static RequestSpecification prepare(RequestSpecification baseSpec,
                                               Map<String, ?> query,
                                               Map<String, String> headers,
                                               Object body) {
        RequestSpecification r = given().spec(baseSpec);
        if (headers != null && !headers.isEmpty()) r.headers(headers);
        if (query   != null && !query.isEmpty())   r.queryParams(query);
        if (body    != null)                       r.body(body);
        return r;
    }
}
