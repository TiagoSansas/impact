package com.sansasdeve.impact.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sansasdeve.impact.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

  public User findByEmail(String email);

}
