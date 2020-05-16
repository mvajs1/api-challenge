package in.reqres.api.models;

import java.util.Date;

public class CreateUser {

    private String id;
    private String password;
    private String email;
    private Date createdAt;

    public CreateUser() {
    }

    private CreateUser(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static class CreateUserBuilder {

        private String password;
        private String email;

        public CreateUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public CreateUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public CreateUser build() {
            return new CreateUser(password, email);
        }
    }
}
