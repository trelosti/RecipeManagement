package com.syberry.magazine.exception;

/**
 * An exception class for mismatch author case.
 */
public class OwnerException extends RuntimeException {
  public OwnerException(String message) {
    super(message);
  }
}
