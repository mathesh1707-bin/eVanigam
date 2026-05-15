package com.example.eVanigam.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.dto.LoginRequest;
import com.example.eVanigam.backend.dto.LoginResponse;
import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.UserRepository;
import com.example.eVanigam.backend.security.JwtUtil;

@Service
public class UserService {
    private UserRepository repo;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    
    public UserService(UserRepository repo,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    //Register
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    //Login
    public LoginResponse loginUser(LoginRequest request) {
        Optional<User> user = repo.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return new LoginResponse("User not found!",null,null);
        }
        User existingUser = user.get();
        boolean isMatch = passwordEncoder.matches(request.getPassword(), existingUser.getPassword());
        if (isMatch) {
            return new LoginResponse("Login successful!", existingUser,jwtUtil.generateToken(request.getEmail()));
        }
        return new LoginResponse("Invalid Password",null,null);
        
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
