package site.skillstory.backend.exception.user;

public class InvalidUserCredentialsException extends RuntimeException {
    public InvalidUserCredentialsException() {
        super("Invalid credentials provided");
    }
}
