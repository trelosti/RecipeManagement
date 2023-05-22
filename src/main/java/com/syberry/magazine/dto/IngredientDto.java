package com.syberry.magazine.dto;

import com.syberry.magazine.enumeration.MeasureUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * A class representing DTO for ingredient.
 */
@Data
@SuperBuilder
@AllArgsConstructor
public class IngredientDto extends BaseDto {
  private String ingredientName;
  private MeasureUnit measureUnit;
  private int quantity;
}
