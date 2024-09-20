package site.skillstory.backend.model.domain.Reqeust;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RequestUserModel {

    private Long id;
    private String username;
    private String password;
}
