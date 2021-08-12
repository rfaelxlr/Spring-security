package com.br.springsecurity;

import com.br.springsecurity.model.Profile;
import com.br.springsecurity.model.User;
import com.br.springsecurity.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@SpringBootApplication
public class SpringsecurityApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setProfile(Profile.ADMIN);
        userRepository.merge(admin);
    }
}
