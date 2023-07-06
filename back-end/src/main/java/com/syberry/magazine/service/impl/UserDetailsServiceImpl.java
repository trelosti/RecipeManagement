package com.syberry.magazine.service.impl;

import com.syberry.magazine.entity.User;
import com.syberry.magazine.entity.UserDetailsImpl;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of user details management service.
 */
@Service
@Primary
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByLogin(username).orElseThrow(
        () -> new EntityNotFoundException(ExceptionMessage.INCORRECT_LOGIN));
    return new UserDetailsImpl(user);
  }
}
