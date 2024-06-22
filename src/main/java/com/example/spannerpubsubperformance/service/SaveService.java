package com.example.spannerpubsubperformance.service;

import com.example.spannerpubsubperformance.entity.User;
import com.example.spannerpubsubperformance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveService {

  private final UserRepository userRepository;

  public SaveService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void execute(String userId, String userName) {
    var user = new User(userId, userName);
    userRepository.save(user);
  }
}
