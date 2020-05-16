package in.reqres.api.models;

public class RegisterUser {

    private String email;
    private String password;
    private String username;

    public RegisterUser() {
    }

    private RegisterUser(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static class RegisterUserBuilder {

        private String email;
        private String password;
        private String username;

        public RegisterUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegisterUserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public RegisterUserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public RegisterUser build() {
            return new RegisterUser(email, password, username);
        }
    }
}
