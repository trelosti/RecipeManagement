package com.syberry.magazine.utils.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class that stores photo-related constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhotoConstant {
  public static final String EMPTY_PHOTO = "none";
  public static final String UPLOAD_DIR = "uploads";
  public static final String UPLOAD_DIR_RELATIVE = "/uploads/";
  public static final String IMG_EXTENSION = ".png";
  public static final int COMPRESSION_VALUE = 4096;
  public static final int PHOTO_SIZE = 5000000;
}
