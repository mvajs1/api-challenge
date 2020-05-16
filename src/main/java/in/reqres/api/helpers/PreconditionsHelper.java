package in.reqres.api.helpers;

import com.github.javafaker.Faker;
import in.reqres.api.models.CreateUser;
import in.reqres.api.models.CreateUser.CreateUserBuilder;
import in.reqres.api.requests.UserRequests;
import org.apache.http.HttpStatus;

public class PreconditionsHelper {

    private final UserRequests userRequests;
    private final Faker faker;

    public PreconditionsHelper() {
        userRequests = new UserRequests();
        faker = new Faker();
    }

    public CreateUser createRandomUser() {
        CreateUser requestBody = new CreateUserBuilder()
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .build();

        return userRequests.postUser(requestBody)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response()
                .as(CreateUser.class);
    }
}
