package site.skillstory.backend.service.implement;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.skillstory.backend.exception.user.UserNotFoundException;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;
import site.skillstory.backend.model.repository.UserRepository;
import site.skillstory.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImplement implements UserService {
    private final UserRepository USER_REPOSITORY;
    private final ModelMapper MODEL_MAPPER;

    @Override
    public List<UserEntity> allUsers() {
        List<UserEntity> users = USER_REPOSITORY.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }
        return users;
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        Optional<UserEntity> user = USER_REPOSITORY.findByUsername(username);
        if (user.isPresent()) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public ResponseEntity<UserEntity> update(Long id, RequestUserModel requestUserModel) {

        Optional<UserEntity> optionalUserEntity = USER_REPOSITORY.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setUsername(requestUserModel.getUsername());
            userEntity.setPassword(requestUserModel.getPassword());
            UserEntity updateUser = USER_REPOSITORY.save(userEntity);
            return ResponseEntity.ok(updateUser);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        Optional<UserEntity> optionalUserEntity = USER_REPOSITORY.findById(id);
        if (optionalUserEntity.isPresent()) {
            USER_REPOSITORY.delete(optionalUserEntity.get());
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
