package com.br.springsecurity.rest;

import com.br.springsecurity.dto.Credentials;
import com.br.springsecurity.repo.UserRepository;
import com.br.springsecurity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthRest {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public AuthRest(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @PreAuthorize("isAdmin")
    @GetMapping(value = "/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello");
    }


    @PostMapping("/signin")
    public UserDetails login(@RequestBody Credentials credentials) {
        return userService.signin(credentials.getEmail(), credentials.getPassword());
    }

    @PostMapping("/signup")
    public UserDetails signup(@RequestBody Credentials credentials) {
        return userService.signup(credentials);
    }
}
