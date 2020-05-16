package in.reqres.api.requests;

import static io.restassured.RestAssured.given;

import in.reqres.api.models.CreateUser;
import in.reqres.configuration.ConfigurationProvider;
import in.reqres.configuration.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHeaders;

public class UserRequests {

    private static final String BASE_PATH = "users";

    public Response getUsers(Map<String, String> queryParams) {
        RequestSpecification spec = new RequestSpecBuilder()
                .addHeader(HttpHeaders.ACCEPT, ContentType.JSON.getAcceptHeader())
                .setBaseUri(ConfigurationProvider.getProperty(Properties.API_USERS_URL))
                .build();

        return given()
                .spec(spec)
                .queryParams(queryParams)
                .get(BASE_PATH);
    }

    public Response getUsers() {
        return getUsers(new HashMap<>());
    }

    public Response getUser(String userId) {
        RequestSpecification spec = new RequestSpecBuilder()
                .addHeader(HttpHeaders.ACCEPT, ContentType.JSON.getAcceptHeader())
                .setBaseUri(ConfigurationProvider.getProperty(Properties.API_USERS_URL))
                .build();

        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .get(String.format("%s/{userId}", BASE_PATH));
    }

    public Response postUser(CreateUser body) {
        RequestSpecification spec = new RequestSpecBuilder()
                .addHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getContentTypeStrings()[0])
                .setBaseUri(ConfigurationProvider.getProperty(Properties.API_USERS_URL))
                .build();

        return given()
                .spec(spec)
                .body(body)
                .post(BASE_PATH);
    }

    public Response deleteUser(String userId) {
        RequestSpecification spec = new RequestSpecBuilder()
                .addHeader(HttpHeaders.ACCEPT, ContentType.JSON.getAcceptHeader())
                .setBaseUri(ConfigurationProvider.getProperty(Properties.API_USERS_URL))
                .build();

        return given()
                .spec(spec)
                .pathParam("userId", userId)
                .delete(String.format("%s/{userId}", BASE_PATH));
    }
}
