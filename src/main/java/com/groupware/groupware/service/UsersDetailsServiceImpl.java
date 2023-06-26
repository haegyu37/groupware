package com.groupware.groupware.service;

import com.groupware.groupware.entity.Users;
import com.groupware.groupware.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UsersDetailsServiceImpl implements UsersDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsServiceImpl(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // Long userId = Long.parseLong(username);
        Users user = usersRepository.findByUserId(String.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId().toString())
                .password(user.getUserPassword())
                .roles(user.getRole().toString())
                .build();
    }
}