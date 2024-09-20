package site.skillstory.backend.exception.user;

public class UserAuthenticationFailedException extends RuntimeException {
    public UserAuthenticationFailedException() {
        super("User authentication failed");
    }

}