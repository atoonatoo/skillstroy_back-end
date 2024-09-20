package site.skillstory.backend.model.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import site.skillstory.backend.model.domain.Reqeust.RequestUserModel;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final RequestUserModel user;

    public CustomUserDetails(RequestUserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
}
