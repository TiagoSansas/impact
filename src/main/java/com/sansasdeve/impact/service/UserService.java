package com.sansasdeve.impact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sansasdeve.impact.domain.user.User;
import com.sansasdeve.impact.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Transactional
  public Page<User> findAll(Pageable pageable) {
    Page<User> users = userRepository.findAll(pageable);
    return users.map(x -> new User(x));
  }

  @Transactional
  public User register(User user) {
    User newUser = new User();
    newUser = userRepository.save(newUser);
    return newUser;
  }

}
