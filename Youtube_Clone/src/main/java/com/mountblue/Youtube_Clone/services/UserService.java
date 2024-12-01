package com.mountblue.Youtube_Clone.services;

import com.mountblue.Youtube_Clone.entities.Users;
import com.mountblue.Youtube_Clone.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(Users user)
    {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Users findByUsername(String username) {
        Optional<Users> userOptional = userRepository.findByUsername(username);

        // Using orElseThrow to handle the absence of the user
        return userOptional.orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
