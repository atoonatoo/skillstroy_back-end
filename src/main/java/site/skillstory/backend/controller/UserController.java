package site.skillstory.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;
import site.skillstory.backend.model.repository.UserRepository;
import site.skillstory.backend.service.UserService;
import site.skillstory.backend.service.implement.CustomUserDetailService;

import java.util.List;
import java.util.Optional;


@Tag(name = "getApi", description = "테스트")
@RequestMapping("/api/users/")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final CustomUserDetailService USER_DETAIL_SERVICE;
    private final UserService SERVICE;
    private final UserRepository USER_REPOSITORY;

    @PostMapping("join")
    public ResponseEntity<Boolean> join(@RequestBody RequestUserModel requestUserModel) {
        return ResponseEntity.ok(USER_DETAIL_SERVICE.join(requestUserModel));
    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok(SERVICE.allUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<UserEntity>> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(SERVICE.getUserByUsername(username));
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseEntity<UserEntity>> update (@PathVariable Long id, @RequestBody RequestUserModel requestUserModel) {
        return ResponseEntity.ok(SERVICE.update(id, requestUserModel));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ResponseEntity<Boolean>> delete (@PathVariable Long id) {
        return ResponseEntity.ok(SERVICE.delete(id));
    }




}
