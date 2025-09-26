package com.practice.blog.services;

import com.practice.blog.dtos.CustomUserDetails;
import com.practice.blog.entities.UserEntity;
import com.practice.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byUserName = userRepository.findByUserName(username);
        if(Objects.isNull(byUserName)){
            throw new UsernameNotFoundException("User not found!");
        }
        return new CustomUserDetails(byUserName);
    }
}
