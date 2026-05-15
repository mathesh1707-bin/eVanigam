package com.example.eVanigam.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eVanigam.backend.model.User;
import com.example.eVanigam.backend.repository.UserRepository;

@Service
public class UserService {
    private UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User addUser(User user) {
        return repo.save(user);
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
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        return repo.save(existingUser);
    }

    public void deleteUser(Long userId) {
        repo.deleteById(userId);
    }
}
