package site.skillstory.backend.exception.user;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("해당 이메일이 존재하지 않습니다.");
    }
}
