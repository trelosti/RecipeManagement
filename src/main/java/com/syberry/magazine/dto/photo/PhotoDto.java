package com.syberry.magazine.dto.photo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A dto class for a photo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDto {
  @NotBlank
  private String name;
  @NotBlank
  private String path;
}
