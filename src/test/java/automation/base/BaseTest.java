package automation.base;

import automation.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

public class BaseTest {
    protected RequestSpecification reqSpec;
    protected ResponseSpecification okJsonSpec;

    @BeforeClass
    public void setup() {
        baseURI  = ConfigManager.get("base.uri");
        basePath = ConfigManager.get("base.path");

        RequestSpecBuilder rsb = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setConfig(config().httpClient(
                        HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", ConfigManager.timeoutMs())
                ))
                .log(LogDetail.METHOD)
                .log(LogDetail.URI);

        String token = ConfigManager.authToken();
        if (token != null && !token.isEmpty() && !token.contains("Primary_Token")) {
            rsb.addHeader("Authorization", token);
        }

        reqSpec = rsb.build();

        okJsonSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
