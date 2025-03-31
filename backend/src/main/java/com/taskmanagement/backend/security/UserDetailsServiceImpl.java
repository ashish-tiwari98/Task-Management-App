package com.taskmanagement.backend.security;

import com.taskmanagement.backend.model.User;
import com.taskmanagement.backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //This method is called by Spring Security when a user logs in.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(authority))
                .build();//Returns a UserDetails object Spring Security can use.
    }
}

/*
We need to provide a custom implementation for UserDetailsService:
Fetches user details from the database.
Converts them into a UserDetails object (which Spring Security understands).
Returns this object for authentication & authorization.
 */
