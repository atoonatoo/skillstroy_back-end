package site.skillstory.backend.service;

import org.springframework.stereotype.Service;
import site.skillstory.backend.model.domain.Reqeust.RequestPostModel;
import site.skillstory.backend.model.domain.Response.ResponsePostModel;
import site.skillstory.backend.model.entity.PostEntity;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {

    Boolean write(RequestPostModel requestPostModel);

    List<ResponsePostModel> findAll();

    Optional<ResponsePostModel> findById(Long id);

    Optional<ResponsePostModel> update(long id, RequestPostModel requestPostModel);

    Boolean delete(Long id);
}
