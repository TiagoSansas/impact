package com.sansasdeve.impact.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sansasdeve.impact.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

  UserDetails findByEmail(String email);

}
