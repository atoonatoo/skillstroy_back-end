package site.skillstory.backend.model.enums;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_MENTOR("ROLE_MENTOR"),
    ROLE_TEACHER("ROLE_TEACHER"),
    ROLE_ADMIN("ROLE_ADMIN");


    private final String value;


    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
