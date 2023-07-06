package com.syberry.magazine.validation.impl;

import com.syberry.magazine.exception.EntityExistsException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * A class for validating input related to users.
 */
@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator {
  private final UserRepository userRepository;

  @Override
  public void validateLoginUniqueness(String login) {
    if (userRepository.existsByLogin(login)) {
      throw new EntityExistsException(String
          .format(ExceptionMessage.USER_EXISTS, login));
    }
  }
}
