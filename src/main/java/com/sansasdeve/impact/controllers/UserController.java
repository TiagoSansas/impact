package com.sansasdeve.impact.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansasdeve.impact.domain.user.RegisterDTO;
import com.sansasdeve.impact.domain.user.User;
import com.sansasdeve.impact.domain.user.UserDTO;
import com.sansasdeve.impact.repositories.UserRepository;
import com.sansasdeve.impact.service.UserService;

@RestController
@RequestMapping(value = "/login")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @GetMapping(value = "/user/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
    Optional<User> optionalResponse = userRepository.findById(id);
    User user = optionalResponse.get();
    UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());
    return ResponseEntity.ok(userDTO);
  }

  @GetMapping(value = "users")
  public ResponseEntity<Page<User>> findALl(Pageable pageable) {
    Page<User> response = userRepository.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "register")
  public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO user) {

    if (this.userRepository.findByEmail(user.email()) != null) {
      return ResponseEntity.badRequest().build();
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());

    User newUser = new User(user.name(), user.email(), encryptedPassword);

    this.userRepository.save(newUser);

    return ResponseEntity.ok().build();
  }

}
