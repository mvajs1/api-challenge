package in.reqres.api.requests;

import static io.restassured.RestAssured.given;

import in.reqres.api.models.RegisterUser;
import in.reqres.configuration.ConfigurationProvider;
import in.reqres.configuration.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;

public class RegisterRequests {

    private static final String BASE_PATH = "register";

    public Response postUser(RegisterUser user) {
        RequestSpecification spec = new RequestSpecBuilder()
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getContentTypeStrings()[0])
                .setBaseUri(ConfigurationProvider.getProperty(Properties.API_USERS_URL))
                .build();

        return given()
                .spec(spec)
                .body(user)
                .post(BASE_PATH);
    }
}
