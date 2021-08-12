package com.br.springsecurity.service;

import com.br.springsecurity.dto.Credentials;
import com.br.springsecurity.exception.CustomException;
import com.br.springsecurity.model.Profile;
import com.br.springsecurity.model.User;
import com.br.springsecurity.repo.UserRepository;
import com.br.springsecurity.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserDetails signin(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()));
            User user = userRepository.findByUsername(username);
            return jwtTokenProvider.createToken(username, user);
        } catch (Exception e) {
            throw new CustomException("Invalid username/password supplied", e);
        }
    }

    @Transactional
    public UserDetails signup(Credentials credentials) {
        if (!userRepository.existByEmail(credentials.getEmail())) {
            User user = new User();
            user.setEmail(credentials.getEmail());
            user.setPassword(passwordEncoder.encode(credentials.getPassword()));
            user.setProfile(Profile.USER);
            userRepository.merge(user);
            return jwtTokenProvider.createToken(user.getEmail(), user);
        }

        throw new CustomException("Username is already in use");
    }
}
