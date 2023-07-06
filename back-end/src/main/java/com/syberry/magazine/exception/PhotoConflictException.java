package com.syberry.magazine.exception;

/**
 * Thrown to indicate that a photo processing failed.
 */
public class PhotoConflictException extends RuntimeException {
  public PhotoConflictException(String message, Throwable ex) {
    super(message);
  }
}
