package com.telerikacademy.newgenerationpuppies.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;



    public UserPrincipal(String name, String username,Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.username = username;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
              list.add(new SimpleGrantedAuthority(user.getAuthority().getAuthority()));

        return new UserPrincipal(
                user.getUserName(),
                user.getPassword(),
                list
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
