package com.syberry.magazine.exception;

import com.syberry.magazine.enumeration.MeasureUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class that stores exception messages.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessage {
  public static final String USER_NOT_FOUND = "User with login: %s is not found";
  public static final String USER_ID_NOT_FOUND = "User with id: %d is not found";
  public static final String USER_EXISTS = "User with this login already exists: %s";
  public static final String INCORRECT_LOGIN = "Incorrect login or password";
  public static final String TOKEN_EXPIRE = "Session time was expired. Please sign in again";
  public static final String TOKEN_NOT_FOUND = "Refresh token is not found. Please sign in";
  public static final String RECIPE_NOT_FOUND = "Recipe with id: %s is not found";
  public static final String OWNER_CONFLICT = "It is forbidden to modify other people's recipes";
  public static final String INVALID_MEASURE_UNIT
      = String.format("Invalid type of ingredient measure unit. Available units: %s",
      MeasureUnit.getNames());
  public static final String PHOTO_COMPRESSION_EXCEPTION =
      "Failed to compress/decompress a photo: %s";

  public static final String PHOTO_NOT_FOUND =
      "Photo with the specified path not found: %s";

  public static final String EMPTY_FILE = "Failed to store empty file: %s";

  public static final String FAILED_TO_STORE = "Failed to store file: %s";

  public static final String FAILED_TO_SAVE = "Failed to save the photo: %s";

  public static final String STORAGE_NOT_INITIALIZED = "Could not initialize storage: %s";

  public static final String FAILED_TO_DELETE_FILE = "Failed to delete files: %s";
}
