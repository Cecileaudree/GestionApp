package com.example.projetA.Services;

import com.example.projetA.Entity.User;
import com.example.projetA.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Optional<User> user = userRepository.findByname(username);

        logger.debug("Loading User by username: {}", username);
        Optional<User> userOptional = userRepository.findByName(username);

        User user = userOptional.orElseThrow(() -> {
            logger.warn("User not found: {}", username);
            return new UsernameNotFoundException("User Not Found");
        });

        logger.debug("User found: {}", username);
        return new MyUserDetail(user);
    }
}
