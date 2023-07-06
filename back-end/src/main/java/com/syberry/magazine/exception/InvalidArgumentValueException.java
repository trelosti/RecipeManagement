package com.syberry.magazine.exception;

/**
 * An exception class for invalid value received case.
 */
public class InvalidArgumentValueException extends RuntimeException {
  public InvalidArgumentValueException(String message) {
    super(message);
  }
}
