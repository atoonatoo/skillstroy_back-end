package site.skillstory.backend.service.implement;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import site.skillstory.backend.exception.post.PostNotFoundException;
import site.skillstory.backend.model.domain.Reqeust.RequestPostModel;
import site.skillstory.backend.model.domain.Response.ResponsePostModel;
import site.skillstory.backend.model.entity.PostEntity;
import site.skillstory.backend.model.repository.PostRepository;
import site.skillstory.backend.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImplement implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public Boolean write(RequestPostModel requestPostModel) {
        PostEntity entity = modelMapper.map(requestPostModel, PostEntity.class);
        postRepository.save(entity);
        return true;
    }

    @Override
    public List<ResponsePostModel> findAll() {
        List<PostEntity> entities = postRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, ResponsePostModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResponsePostModel> findById(Long id) {
        return postRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ResponsePostModel.class));
    }

    @Override
    public Optional<ResponsePostModel> update(long id, RequestPostModel requestPostModel) {
        Optional<PostEntity> optionalEntity = postRepository.findById(id);

        if (optionalEntity.isPresent()) {
            PostEntity updateEntity = optionalEntity.get();
            updateEntity.setTitle(requestPostModel.getTitle());
            updateEntity.setDescription(requestPostModel.getDescription());
            PostEntity savedEntity = postRepository.save(updateEntity);
            ResponsePostModel response = modelMapper.map(savedEntity, ResponsePostModel.class);

            return Optional.of(response);
        }

        return Optional.empty();
    }

    @Override
    public Boolean delete(Long id) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()) {
            postRepository.delete(optionalPostEntity.get());
            return true;
        } else {
            throw new PostNotFoundException();
        }
    }

    @Override
    public Page<PostEntity> pagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return postRepository.findAll(pageRequest);
    }
}
