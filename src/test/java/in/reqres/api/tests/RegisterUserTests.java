package in.reqres.api.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.javafaker.Faker;
import in.reqres.api.models.RegisterUser;
import in.reqres.api.models.RegisterUser.RegisterUserBuilder;
import in.reqres.api.requests.RegisterRequests;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
public class RegisterUserTests {

    private static RegisterRequests registerRequests;
    private static Faker faker;

    @BeforeAll
    public static void beforeAll() {
        registerRequests = new RegisterRequests();
        faker = new Faker();
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should not be able to register without e-mail")
    public void shouldNotBeAbleToRegisterWithoutEmailTest() {
        // Creating user without e-mail
        RegisterUser requestBody = new RegisterUserBuilder()
                .password(faker.internet().password())
                .build();

        Response response = registerRequests.postUser(requestBody);

        assertAll(
                () -> assertThat(String.format("Unexpected status code: %s.", response.statusCode()),
                        response.statusCode(), is(equalTo(HttpStatus.SC_BAD_REQUEST))),
                () -> assertThat(response.getBody().jsonPath().get("error"), is(equalTo("Missing email or username")))
        );
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should not be able to register without password")
    public void shouldNotBeAbleToRegisterWithoutPasswordTest() {
        // Creating user without password
        RegisterUser requestBody = new RegisterUserBuilder()
                .email(faker.internet().emailAddress())
                .build();

        Response response = registerRequests.postUser(requestBody);

        assertAll(
                () -> assertThat(String.format("Unexpected status code: %s.", response.statusCode()),
                        response.statusCode(), is(equalTo(HttpStatus.SC_BAD_REQUEST))),
                () -> assertThat(response.getBody().jsonPath().get("error"), is(equalTo("Missing password")))
        );
    }

    @Test
    @Tag("smoke")
    @Execution(ExecutionMode.SAME_THREAD)
    @DisplayName("Should not be able to register user that is not defined")
    public void shouldNotBeAbleToRegisterThatIsNotDefinedTest() {
        // Creating user with username that is not defined
        RegisterUser requestBody = new RegisterUserBuilder()
                .username(faker.name().username())
                .password(faker.internet().password())
                .build();

        Response response = registerRequests.postUser(requestBody);

        assertAll(
                () -> assertThat(String.format("Unexpected status code: %s.", response.statusCode()),
                        response.statusCode(), is(equalTo(HttpStatus.SC_BAD_REQUEST))),
                () -> assertThat(response.getBody().jsonPath().get("error"),
                        is(equalTo("Note: Only defined users succeed registration")))
        );
    }
}
