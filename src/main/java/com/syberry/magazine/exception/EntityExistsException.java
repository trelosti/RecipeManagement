package com.syberry.magazine.exception;

/**
 * An exception class for existing entity case.
 */
public class EntityExistsException extends EntityException {
  public EntityExistsException(String message) {
    super(message);
  }
}
