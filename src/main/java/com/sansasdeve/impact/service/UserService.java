package com.sansasdeve.impact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sansasdeve.impact.domain.user.User;
import com.sansasdeve.impact.repositories.UserRepository;
import com.sansasdeve.impact.service.exceptions.ResourceNotFoundException;

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
  public User findById(Long id) {
    User searchUser = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
    return new User(searchUser);
  }

  @Transactional
  public User register(User user) {
    User newUser = new User(user);
    newUser = userRepository.save(newUser);
    return newUser;
  }

  @Transactional
  public User update(Long id, User user) {
    User newEntity = userRepository.getReferenceById(id);
    newEntity.setName(user.getName());
    newEntity.setPassword(user.getPassword());
    return newEntity;
  }

}
