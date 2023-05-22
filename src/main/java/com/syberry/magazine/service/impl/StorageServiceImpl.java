package com.syberry.magazine.service.impl;

import com.syberry.magazine.dto.file.BaseFile;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.StorageException;
import com.syberry.magazine.repository.PhotoRepository;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.StorageService;
import com.syberry.magazine.utils.SecurityUtil;
import com.syberry.magazine.utils.file.PhotoConstant;
import com.syberry.magazine.utils.file.PhotoUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * The implementation of storage management service.
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
  private final Path rootLocation;

  private final PhotoRepository photoRepository;

  private final UserRepository userRepository;

  /**
   * The constructor method to initialize path and inject dependencies.
   *
   * @param photoRepository PhotoRepository
   * @param userRepository UserRepository
   */
  @Autowired
  public StorageServiceImpl(PhotoRepository photoRepository, UserRepository userRepository) {
    this.rootLocation = Paths.get(PhotoConstant.UPLOAD_DIR);
    this.photoRepository = photoRepository;
    this.userRepository = userRepository;
  }

  @Override
  public BaseFile store(MultipartFile file) {
    String fileName = UUID.randomUUID() + PhotoConstant.IMG_EXTENSION;

    try {
      if (file.isEmpty()) {
        throw new StorageException(String.format(ExceptionMessage.EMPTY_FILE, fileName));
      }

      Path targetLocation = this.rootLocation.resolve(fileName);

      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return BaseFile.builder()
          .name(fileName)
          .path(ServletUriComponentsBuilder
              .fromCurrentContextPath()
              .path(PhotoConstant.UPLOAD_DIR_RELATIVE + fileName)
              .toUriString())
          .build();

    } catch (IOException e) {
      throw new StorageException(String.format(ExceptionMessage.FAILED_TO_STORE, fileName), e);
    }
  }

  @Override
  public void deleteUserPhoto() {
    Long id = SecurityUtil.getUserDetails().getId();
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String
            .format(ExceptionMessage.USER_ID_NOT_FOUND, id)));

    String fileName = user.getUserPhoto() == null ? PhotoConstant.EMPTY_PHOTO :
        user.getUserPhoto().getName();

    Path filePath = Paths.get(PhotoConstant.UPLOAD_DIR, fileName);

    try {
      Files.delete(filePath);
      log.info("The file {} was deleted.", fileName);
    } catch (IOException e) {
      log.info("Exception occurred while deleting file: {}", fileName);
      throw new StorageException(String.format(ExceptionMessage.FAILED_TO_DELETE_FILE, fileName), e);
    }
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);

    } catch (IOException e) {
      throw new StorageException(String.format(ExceptionMessage.STORAGE_NOT_INITIALIZED,
          e.getMessage()), e);
    }

    List<Photo> photoList = photoRepository.findAll();

    photoList
        .forEach(photo -> {
          String fileName = photo.getName();
          try {
            Path targetLocation = this.rootLocation.resolve(fileName);

            byte[] decompressedImage = PhotoUtil.decompressPhoto(photo.getData());

            Files.copy(new ByteArrayInputStream(decompressedImage),
                targetLocation, StandardCopyOption.REPLACE_EXISTING);

          } catch (IOException e) {
            throw new StorageException(String.format(ExceptionMessage.FAILED_TO_STORE, fileName), e);
          }
        });
  }
}
