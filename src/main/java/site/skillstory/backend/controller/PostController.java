package site.skillstory.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.skillstory.backend.model.domain.Reqeust.RequestPostModel;
import site.skillstory.backend.model.domain.Response.ResponsePostModel;
import site.skillstory.backend.model.entity.PostEntity;
import site.skillstory.backend.service.PostService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService service;


    @PostMapping("/")
    public ResponseEntity<Boolean> write(@Valid @RequestBody RequestPostModel requestPostModel) {
        return ResponseEntity.ok(service.write(requestPostModel));
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponsePostModel>> getAllPosts () {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ResponsePostModel>> getPost (@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<ResponsePostModel>> update (@PathVariable long id, @RequestBody RequestPostModel requestPostModel) {
        return  ResponseEntity.ok(service.update(id, requestPostModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete (@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping
    public Page<PostEntity> pagination (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.pagination(page, size);
    }
}
