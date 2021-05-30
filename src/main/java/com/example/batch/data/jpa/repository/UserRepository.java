package com.example.batch.data.jpa.repository;

import com.example.batch.data.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
