package site.skillstory.backend.service.implement;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.skillstory.backend.exception.user.InvalidEmailFormatException;
import site.skillstory.backend.exception.user.UserAlreadyExistsException;
import site.skillstory.backend.exception.user.UserNotFoundException;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;
import site.skillstory.backend.model.repository.UserRepository;
import site.skillstory.backend.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    @Override
    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserByUsername(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public ResponseEntity<UserEntity> update(Long id, RequestUserModel requestUserModel) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setUsername(requestUserModel.getUsername());
        userEntity.setPassword(passwordEncoder.encode(requestUserModel.getPassword()));

        UserEntity updatedUser = userRepository.save(userEntity);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException();
        }

        userRepository.delete(optionalUserEntity.get());
        return ResponseEntity.ok(true);
    }

    @Override
    public String findUsername(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailFormatException();
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        return userRepository.findByUsername(email).get().getUsername();
    }

    public Boolean join(RequestUserModel requestUserModel) {

        if (userRepository.findByUsername(requestUserModel.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        requestUserModel.setPassword(passwordEncoder.encode(requestUserModel.getPassword()));  // 비밀번호 암호화
        UserEntity entity = modelMapper.map(requestUserModel, UserEntity.class);
        userRepository.save(entity);

        return true;
    }

}
