package site.skillstory.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserEntity> allUsers();

    Optional<UserEntity> getUserByUsername(String username);

    ResponseEntity<UserEntity> update(Long id, RequestUserModel requestUserModel);

    ResponseEntity<Boolean> delete(Long id);
}
