package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
