package com.example.eVanigam.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.dto.LoginRequest;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class UserService {
    private UserRepository repo;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo,PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }
    //Register
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    //Login
    public String loginUser(LoginRequest request) { 
        Optional<User> user = repo.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return "User not found!";
        }
        User existingUser = user.get();
        boolean isMatch = passwordEncoder.matches(request.getPassword(), existingUser.getPassword());
        if (isMatch) {
            return "Login successful!";
        }
        return "Invalid Password";
        
    }

    public List<User> getUsers() {
        return repo.findAll();
    }

    public User getUserById(Long userId) {
        return repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User updateUser(Long userId, User user) {
        User existingUser = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setRole(user.getRole());
        return repo.save(existingUser);
    }

    public void deleteUser(Long userId) {
        repo.deleteById(userId);
    }
}
