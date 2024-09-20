package site.skillstory.backend.model.domain.Reqeust;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;
import site.skillstory.backend.model.entity.UserEntity;

import java.time.LocalDate;

@Data
@Component
public class RequestPostModel {
    private Long id;
    @NotBlank(message = "Title is required")  // 필수값 설정
    private String title;
    @NotBlank(message = "Title is required")  // 필수값 설정
    private String description;
    private LocalDate entryDate;
    private LocalDate modifyDate;
    private Long views;
    private UserEntity user;

}
