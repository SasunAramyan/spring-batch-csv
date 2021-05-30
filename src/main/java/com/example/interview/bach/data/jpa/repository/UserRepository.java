package com.example.interview.bach.data.jpa.repository;

import com.example.interview.bach.data.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
