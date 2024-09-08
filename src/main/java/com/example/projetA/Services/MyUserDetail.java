package com.example.projetA.Services;

import com.example.projetA.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class MyUserDetail implements UserDetails {

    private User user;

    public  MyUserDetail (User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole()+"";
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() {return user.getName();}

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
