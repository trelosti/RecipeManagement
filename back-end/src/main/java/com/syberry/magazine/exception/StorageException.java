package com.syberry.magazine.exception;

/**
 * An exception class for general storage error cases.
 */
public class StorageException extends RuntimeException {
  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, Throwable ex) {
    super(message);
  }
}
