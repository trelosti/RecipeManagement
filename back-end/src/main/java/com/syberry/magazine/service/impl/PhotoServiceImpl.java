package com.syberry.magazine.service.impl;

import com.syberry.magazine.converter.PhotoConverter;
import com.syberry.magazine.dto.file.BaseFile;
import com.syberry.magazine.dto.photo.PhotoCreatingDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.Step;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.repository.PhotoRepository;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.PhotoService;
import com.syberry.magazine.service.StorageService;
import com.syberry.magazine.utils.SecurityUtil;
import com.syberry.magazine.utils.file.PhotoConstant;
import com.syberry.magazine.utils.file.PhotoUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of photo management service.
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
  private final PhotoRepository photoRepository;
  private final UserRepository userRepository;
  private final StorageService storageService;

  @Override
  @Transactional
  public Photo createUserPhoto(PhotoCreatingDto photo) {
    Photo userPhoto = Photo.builder()
        .name(photo.getName())
        .path(photo.getPath())
        .data(PhotoUtil.compressPhoto(photo.getData()))
        .build();

    Long userId = SecurityUtil.getUserDetails().getId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException(String
            .format(ExceptionMessage.USER_ID_NOT_FOUND, userId)));

    photoRepository.save(userPhoto);

    user.setUserPhoto(userPhoto);
    return userPhoto;
  }

  @Override
  @Transactional
  public PhotoDto saveUserPhoto(MultipartFile file) throws IOException {
    BaseFile storedFile = storageService.store(file);

    PhotoCreatingDto dto = PhotoCreatingDto.builder()
        .name(storedFile.getName())
        .data(file.getBytes())
        .path(storedFile.getPath())
        .build();

    Photo userPhoto = createUserPhoto(dto);
    return PhotoConverter.convertToDto(userPhoto);
  }

  @Override
  @Transactional
  public void updateUserPhoto(PhotoCreatingDto photo) {
    storageService.deleteUserPhoto();

    Long id = SecurityUtil.getUserDetails().getId();
    User user = userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String
            .format(ExceptionMessage.USER_ID_NOT_FOUND, id)));

    String name = user.getUserPhoto() == null ? PhotoConstant.EMPTY_PHOTO :
        user.getUserPhoto().getName();

    Photo userPhoto = photoRepository.findByName(name).get();
    userPhoto.setName(photo.getName());
    userPhoto.setData(PhotoUtil.compressPhoto(photo.getData()));
    userPhoto.setPath(photo.getPath());
  }

  @Override
  @Transactional
  public void changeUserPhoto(MultipartFile file) throws IOException {
    BaseFile storedFile = storageService.store(file);

    PhotoCreatingDto dto = PhotoCreatingDto.builder()
        .name(storedFile.getName())
        .data(file.getBytes())
        .path(storedFile.getPath())
        .build();

    updateUserPhoto(dto);
  }

  /**
   * This method is responsible for creating and setting a recipe photo.
   *
   * @param recipe a recipe for photo
   * @param photo a photo to set
   */
  public void createRecipePhoto(Recipe recipe, PhotoCreatingDto photo) {
    String path = photo.getPath();

    Photo recipePhoto = Photo.builder()
        .name(photo.getName())
        .path(path)
        .data(PhotoUtil.compressPhoto(photo.getData()))
        .build();

    recipe.setPhoto(recipePhoto);
  }

  /**
   * This method is responsible for creating and setting a step photo.
   *
   * @param step a step for photo
   * @param photo a photo to set
   */
  public void createStepPhoto(Step step, PhotoCreatingDto photo) {
    String path = photo.getPath();

    Photo stepPhoto = Photo.builder()
        .name(photo.getName())
        .path(path)
        .data(PhotoUtil.compressPhoto(photo.getData()))
        .build();

    step.setPhoto(stepPhoto);
  }

  @Override
  public void saveRecipePhoto(Recipe recipe, MultipartFile photo) throws IOException {
    BaseFile storedFile = storageService.store(photo);

    PhotoCreatingDto dto = PhotoCreatingDto.builder()
        .name(storedFile.getName())
        .data(photo.getBytes())
        .path(storedFile.getPath())
        .build();

    createRecipePhoto(recipe, dto);
  }

  @Override
  public void saveStepPhoto(Step step, MultipartFile photo) throws IOException {
    BaseFile storedFile = storageService.store(photo);

    PhotoCreatingDto dto = PhotoCreatingDto.builder()
        .name(storedFile.getName())
        .data(photo.getBytes())
        .path(storedFile.getPath())
        .build();

    createStepPhoto(step, dto);
  }
}
