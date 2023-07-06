package com.syberry.magazine.converter;

import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.entity.Photo;

/**
 * A class for converting Photo entity to dto.
 */
public class PhotoConverter {
  /**
   * This method is responsible for converting Photo entity to PhotoDto.
   *
   * @param entity entity to be converted
   * @return PhotoDto
   */
  public static PhotoDto convertToDto(Photo entity) {
    return PhotoDto.builder()
        .name(entity.getName())
        .path(entity.getPath())
        .build();
  }
}
