package com.syberry.magazine.converter;

import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.utils.file.PhotoConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class for converting user entity to dto and vice versa.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {
  /**
   * This method is responsible for converting User entity to UserDto.
   *
   * @param entity entity to be converted
   * @return UserDto
   */
  public static UserDto convertToDto(User entity) {
    String photoPath = entity.getUserPhoto() == null ? PhotoConstant.EMPTY_PHOTO :
        entity.getUserPhoto().getPath();

    return UserDto.builder()
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .login(entity.getLogin())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .userPhotoLink(photoPath)
        .build();
  }

  /**
   * This method is responsible for converting UserCreatingDto to User entity.
   *
   * @param dto UserCreatingDto object to be converted
   * @return User
   */
  public static User convertToEntity(UserCreatingDto dto) {
    return User.builder()
        .login(dto.getLogin())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .build();
  }

  /**
   * This method is responsible for converting UserCreatingDto to User entity.
   *
   * @param dto UserDto object to be converted
   * @return User
   */
  public static User convertToEntity(UserDto dto) {
    return User.builder()
        .id(dto.getId())
        .createdAt(dto.getCreatedAt())
        .updatedAt(dto.getUpdatedAt())
        .login(dto.getLogin())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .build();
  }

  /**
   * This method is responsible for converting UserDto to User entity with photo.
   *
   * @param dto UserDto object to be converted
   * @return User
   */
  public static User convertToEntity(UserDto dto, Photo userPhoto) {
    return User.builder()
        .id(dto.getId())
        .createdAt(dto.getCreatedAt())
        .updatedAt(dto.getUpdatedAt())
        .login(dto.getLogin())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .userPhoto(userPhoto)
        .build();
  }
}
