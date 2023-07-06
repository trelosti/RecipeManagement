package com.syberry.magazine.validation;

import com.syberry.magazine.exception.EntityExistsException;

/**
 * An interface for validating input related to users.
 */
public interface UserValidator {

  /**
   * Validates if login already exists and throws a EntityExistsException if it is.
   *
   * @param login login to validate
   * @throws EntityExistsException if login already in use
   */
  void validateLoginUniqueness(String login);
}
