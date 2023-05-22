package com.syberry.magazine.service;

import com.syberry.magazine.dto.file.BaseFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * Storage management service.
 */
public interface StorageService {
  /**
   * This method is responsible for storing a new file within the application directory.
   *
   * @param file The file to be stored
   * @return BaseFile object
   */
  BaseFile store(MultipartFile file);

  /**
   * This method is responsible for deleting an existing file from the application directory.
   */
  void deleteUserPhoto();

  /**
   * This method is responsible for initializing the upload directory
   * and load photo data from database to it.
   */
  void init();
}
