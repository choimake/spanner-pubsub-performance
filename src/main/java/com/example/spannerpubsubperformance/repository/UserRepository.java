package com.example.spannerpubsubperformance.repository;

import com.example.spannerpubsubperformance.entity.User;
import com.example.spannerpubsubperformance.entity.UserPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserPk> {}
