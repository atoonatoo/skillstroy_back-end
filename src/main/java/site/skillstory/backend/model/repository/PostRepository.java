package site.skillstory.backend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.skillstory.backend.model.entity.PostEntity;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAll (Pageable pageable);
}
