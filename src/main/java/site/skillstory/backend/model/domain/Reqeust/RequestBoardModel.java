package site.skillstory.backend.model.domain.Reqeust;

import lombok.Data;
import org.springframework.stereotype.Component;
import site.skillstory.backend.model.entity.UserEntity;

import java.time.LocalDate;

@Data
@Component
public class RequestBoardModel {

    private Long id;
    private String title;
    private String description;
    private LocalDate entryDate;
    private LocalDate modifyDate;
    private Long views;
    private UserEntity user;

}
