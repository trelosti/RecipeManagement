package com.syberry.magazine.converter;

import com.syberry.magazine.dto.StepDto;
import com.syberry.magazine.entity.Step;
import com.syberry.magazine.utils.file.PhotoConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class for converting Step entity to dto and vice versa.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StepConverter {

  /**
   * This method is responsible for converting Step entity to StepDto.
   *
   * @param entity step entity to be converted
   * @return StepDto
   */
  public static StepDto convertToDto(Step entity) {
    String link = entity.getPhoto() == null ? PhotoConstant.EMPTY_PHOTO :
        entity.getPhoto().getPath();

    return StepDto.builder()
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .description(entity.getDescription())
        .stepPhotoLink(link)
        .build();
  }
}
