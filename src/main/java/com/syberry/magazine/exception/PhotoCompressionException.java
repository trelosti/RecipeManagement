package com.syberry.magazine.exception;

/**
 * Thrown to indicate that a photo compression failed.
 */
public class PhotoCompressionException extends RuntimeException {
  public PhotoCompressionException(String message, Throwable ex) {
    super(message);
  }
}
