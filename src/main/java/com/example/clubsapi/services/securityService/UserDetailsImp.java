package com.example.clubsapi.services.securityService;

import com.example.clubsapi.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImp implements UserDetails {
    private int id;

    private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImp build(UserEntity user) {
        List<GrantedAuthority> authorityList = user.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImp(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorityList
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
