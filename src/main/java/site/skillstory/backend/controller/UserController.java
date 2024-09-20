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

    // 로그인 성공 시 처리
    @GetMapping("/authSuccess")
    public ResponseEntity<String> authSuccess() {
        return ResponseEntity.ok("Login Successful");
    }

    // 로그인 실패 시 처리
    @GetMapping("/authFail")
    public ResponseEntity<String> authFail() {
        return ResponseEntity.status(401).body("Login Failed");
    }

    // 로그아웃 성공 시 처리
    @GetMapping("/logOutSuccess")
    public ResponseEntity<String> logOutSuccess() {
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody RequestUserModel requestUserModel) {
        // 로그인 처리 로직
        return ResponseEntity.ok("Authenticated");
    }

    // 뷰로 반환할 프론트가 없어서
    // Resolved [org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported]
    // 오류가 생기는 것으로 추정
    // 간단하게 view를 만들어 보도록 할 것이다.

}
