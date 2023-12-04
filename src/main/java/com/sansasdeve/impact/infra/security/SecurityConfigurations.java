package com.sansasdeve.impact.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Bean
  public DefaultSecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .headers(headers -> headers.frameOptions().disable())
        .csrf(csrf -> csrf.disable())
        .build();
  }

  @Bean
  public PasswordEncoder passwordEnconder() {
    return new BCryptPasswordEncoder();
  }
}
