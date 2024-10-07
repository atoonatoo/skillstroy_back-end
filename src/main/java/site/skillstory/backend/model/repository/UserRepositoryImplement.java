package site.skillstory.backend.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import site.skillstory.backend.model.entity.QUserEntity;
import site.skillstory.backend.model.entity.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImplement implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        QUserEntity user = QUserEntity.userEntity;
        UserEntity result = jpaQueryFactory.selectFrom(user)
                .where(user.username.eq(username))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        QUserEntity user = QUserEntity.userEntity;
        UserEntity result = jpaQueryFactory.selectFrom(user)
                .where(user.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(result);
    }


//    @Override
//    public Optional<UserEntity> findByUsername(String username) {
//
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<UserEntity> findByEmail(String email) {
//        return Optional.empty();
//    }
}
