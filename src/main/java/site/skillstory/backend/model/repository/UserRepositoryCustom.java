package site.skillstory.backend.model.repository;

import site.skillstory.backend.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
