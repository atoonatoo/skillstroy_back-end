package site.skillstory.backend.model.domain.Response;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseUserModel {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String gender;
    private String address;
}
