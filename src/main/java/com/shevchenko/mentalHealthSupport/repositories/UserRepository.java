package com.shevchenko.mentalHealthSupport.repositories;

import com.shevchenko.mentalHealthSupport.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> fingByUsername(String username);
    Optional<User> findByEmail(String email);
}
