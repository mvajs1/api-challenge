package in.reqres.api.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import com.github.javafaker.Faker;
import in.reqres.api.helpers.PreconditionsHelper;
import in.reqres.api.models.CreateUser;
import in.reqres.api.models.GetUser;
import in.reqres.api.requests.UserRequests;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class UserTests {

    private static UserRequests userRequests;
    private static Faker faker;

    private CreateUser user;

    @BeforeAll
    public static void beforeAll() {
        userRequests = new UserRequests();
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        // Create random user in the system
        user = new PreconditionsHelper().createRandomUser();
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should be able list all users")
    public void shouldListAllUsersTest() {
        // Get all users
        Response response = userRequests.getUsers();

        assertThat(String.format("Unexpected status code: %s.", response.statusCode()), response.statusCode(),
                is(equalTo(HttpStatus.SC_OK)));

        // Verify user list is not empty (we have created a user before)
        GetUser[] users = response
                .then()
                .extract()
                .response()
                .jsonPath()
                .getObject("data", GetUser[].class);

        assertThat("User list is empty.", Arrays.asList(users), is(not(empty())));
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should be able to delete existing user")
    public void shouldDeleteExistingUserTest() {
        // Delete user
        Response deleteResponse = userRequests.deleteUser(user.getId());

        assertThat(String.format("Unexpected status code: %s.", deleteResponse.statusCode()),
                deleteResponse.statusCode(), is(equalTo(HttpStatus.SC_NO_CONTENT)));

        // Get user and verify it does not exist
        Response getResponse = userRequests.getUser(user.getId());

        assertThat(String.format("Unexpected status code: %s.", getResponse.statusCode()), getResponse.statusCode(),
                is(equalTo(HttpStatus.SC_NOT_FOUND)));
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should be able to list all users with delay")
    public void shouldListAllUsersWithDelayTest() {
        // Get all users with delay
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("delay", "5");

        Response response = userRequests.getUsers(queryParams);

        assertThat(String.format("Unexpected status code: %s.", response.statusCode()), response.statusCode(),
                is(equalTo(HttpStatus.SC_OK)));

        // Verify user list is not empty (we have created a user before)
        GetUser[] users = response
                .then()
                .extract()
                .response()
                .jsonPath()
                .getObject("data", GetUser[].class);

        assertThat("User list is empty.", Arrays.asList(users), is(not(empty())));
    }
}
