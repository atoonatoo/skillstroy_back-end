package site.skillstory.backend.exception.user;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("사용자가 이미 존재합니다.");
    }
}