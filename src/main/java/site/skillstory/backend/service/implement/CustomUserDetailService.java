package site.skillstory.backend.service.implement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.skillstory.backend.exception.user.UserAlreadyExistsException;
import site.skillstory.backend.exception.user.UserNotFoundException;
import site.skillstory.backend.model.domain.CustomUserDetails;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;
import site.skillstory.backend.model.entity.UserEntity;
import site.skillstory.backend.model.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository USER_REPOSITORY;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final ModelMapper MODEL_MAPPER;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {

        this.USER_REPOSITORY = userRepository;
        this.PASSWORD_ENCODER = passwordEncoder;
        this.MODEL_MAPPER = modelMapper;
    }

    @Override
    @Transactional(readOnly = true) // 읽기 전용 작업으로 선언
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUserEntity = USER_REPOSITORY.findByUsername(username);
        UserEntity userEntity = optionalUserEntity.orElseThrow(UserNotFoundException::new);
        RequestUserModel requestUserModel = new RequestUserModel();
        requestUserModel.setUsername(optionalUserEntity.get().getUsername());
        requestUserModel.setPassword(optionalUserEntity.get().getPassword());

        return new CustomUserDetails(requestUserModel);
    }

    public Boolean join(RequestUserModel requestUserModel) {

        if(USER_REPOSITORY.findByUsername(requestUserModel.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        requestUserModel.setPassword(PASSWORD_ENCODER.encode(requestUserModel.getPassword()));
        UserEntity entity = MODEL_MAPPER.map(requestUserModel, UserEntity.class);
        USER_REPOSITORY.save(entity);

        return true;
    }
}
