package com.syberry.magazine.dto.photo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A dto class for creating a new photo.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoCreatingDto {
  @NotBlank
  private String name;
  @NotBlank
  private String path;
  private byte[] data;
}
