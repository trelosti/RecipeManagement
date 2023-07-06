package com.syberry.magazine.service;

import com.syberry.magazine.dto.photo.PhotoCreatingDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.Step;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Photo management service.
 */
public interface PhotoService {
  /**
   * This method is responsible for creating a new user photo.
   *
   * @param file A PhotoCreationDto object representing a file
   * @return A PhotoDto object
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  Photo createUserPhoto(PhotoCreatingDto file) throws IOException;

  /**
   * This method is responsible for saving a user photo to storage.
   *
   * @param file the photo
   * @return PhotoDto
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  PhotoDto saveUserPhoto(MultipartFile file) throws IOException;

  /**
   * This method is responsible for updating a user photo.
   *
   * @param photoCreatingDto A PhotoCreationDto object to update a photo.
   */
  void updateUserPhoto(PhotoCreatingDto photoCreatingDto);

  /**
   * This method is responsible for changing a user photo with another photo file.
   *
   * @param file a new file
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  void changeUserPhoto(MultipartFile file) throws IOException;

  /**
   * This method is responsible for saving a recipe photo.
   *
   * @param recipe the recipe
   * @param photo the photo for the recipe
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  void saveRecipePhoto(Recipe recipe, MultipartFile photo) throws IOException;


  /**
   * This method is responsible for saving a step photo.
   *
   * @param step the step
   * @param photo the photo for the step
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  void saveStepPhoto(Step step, MultipartFile photo) throws IOException;
}
