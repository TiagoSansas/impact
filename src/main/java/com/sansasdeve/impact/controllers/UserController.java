package com.sansasdeve.impact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sansasdeve.impact.domain.user.AutheticationDTO;
import com.sansasdeve.impact.domain.user.LoginResponseDTO;
import com.sansasdeve.impact.domain.user.RegisterDTO;
import com.sansasdeve.impact.domain.user.User;
import com.sansasdeve.impact.domain.user.UserRole;
import com.sansasdeve.impact.infra.security.TokenService;
import com.sansasdeve.impact.repositories.UserRepository;
import com.sansasdeve.impact.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AutheticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((User) auth.getPrincipal());
    return ResponseEntity.ok(new LoginResponseDTO(token));
  }

  @GetMapping(value = "/users/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    User response = userService.findById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "users")
  public ResponseEntity<Page<User>> findALl(Pageable pageable) {
    Page<User> response = userRepository.findAll(pageable);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "register")
  public ResponseEntity<RegisterDTO> register(@RequestBody @Valid RegisterDTO data) {

    if (this.userRepository.findByEmail(data.email()) != null) {
      return ResponseEntity.badRequest().build();
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

    User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.USER);

    this.userService.register(newUser);
    return ResponseEntity.ok().build();
  }

  @PutMapping(value = "users/{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User entity) {

    String encryptedPassword = new BCryptPasswordEncoder().encode(entity.getPassword());
    entity.setPassword(encryptedPassword);

    entity = userService.update(id, entity);
    return ResponseEntity.ok().build();
  }

}
