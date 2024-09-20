package site.skillstory.backend.service;

import org.springframework.http.ResponseEntity;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserEntity> allUsers();

    Optional<UserEntity> getUserByUsername(Long id);

    ResponseEntity<UserEntity> update(Long id, RequestUserModel requestUserModel);

    ResponseEntity<Boolean> delete(Long id);
}
