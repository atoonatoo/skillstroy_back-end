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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final CustomUserDetailService userDetailService;
    private final UserService service;

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody RequestUserModel requestUserModel) {
        return ResponseEntity.ok(userDetailService.join(requestUserModel));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok(service.allUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserEntity>> getUserByUsername(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserByUsername(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntity<UserEntity>> update (@PathVariable Long id, @RequestBody RequestUserModel requestUserModel) {
        return ResponseEntity.ok(service.update(id, requestUserModel));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<Boolean>> delete (@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }




}
