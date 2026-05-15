package com.example.eVanigam.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eVanigam.backend.model.User;

public interface UserRepository extends JpaRepository <User,Long>  {
    
}
