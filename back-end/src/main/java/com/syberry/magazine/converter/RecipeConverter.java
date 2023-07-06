package com.syberry.magazine.converter;

import com.syberry.magazine.dto.IngredientDto;
import com.syberry.magazine.dto.RecipeCreatingDto;
import com.syberry.magazine.dto.RecipeDetailsDto;
import com.syberry.magazine.dto.RecipeDto;
import com.syberry.magazine.dto.StepDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.entity.UserDetailsImpl;
import com.syberry.magazine.utils.SecurityUtil;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A class for converting Recipe entity to dto and vice versa.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipeConverter {

  /**
   * This method is responsible for converting Recipe entity to RecipeDto.
   *
   * @param entity entity to be converted
   * @return RecipeDto
   */
  public static RecipeDto convertToDto(Recipe entity) {
    boolean saved = checkSave(entity);

    return RecipeDto.builder()
        .id(entity.getId())
        .recipeName(entity.getRecipeName())
        .recipePhotoLink(entity.getPhoto().getPath())
        .saved(saved)
        .build();
  }

  /**
   * This method is responsible for converting Recipe entity to RecipeDetailsDto.
   *
   * @param entity entity to be converted
   * @return RecipeDto
   */
  public static RecipeDetailsDto convertToDetailsDto(Recipe entity) {
    List<StepDto> stepDtos = entity.getSteps().stream()
        .map(StepConverter::convertToDto)
        .toList();

    List<IngredientDto> ingredientDtos = entity.getIngredients().stream()
        .map(IngredientConverter::convertToDto)
        .toList();

    UserDto userDto = UserConverter.convertToDto(entity.getAuthor());

    boolean saved = checkSave(entity);

    return RecipeDetailsDto.builder()
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .recipeName(entity.getRecipeName())
        .author(userDto)
        .recipeSteps(stepDtos)
        .ingredients(ingredientDtos)
        .cookTime(entity.getCookTime())
        .saved(saved)
        .recipePhotoLink(entity.getPhoto().getPath())
        .build();
  }

  /**
   * This method is responsible for converting RecipeCreatingDto to Recipe entity.
   *
   * @param dto dto to be converted
   * @return Recipe
   */
  public static Recipe convertToEntity(RecipeCreatingDto dto) {
    return Recipe.builder()
        .recipeName(dto.getRecipeName())
        .cookTime(dto.getCookTime())
        .build();
  }

  private static boolean checkSave(Recipe entity) {
    if (entity.getSaves() == null) {
      return false;
    }

    UserDetailsImpl userDetails = SecurityUtil.getUserDetails();
    Long currentUserId = userDetails.getId();

    Optional<User> optionalSave = entity.getSaves().stream()
        .filter(save -> save.getId().equals(currentUserId))
        .findFirst();
    return optionalSave.isPresent();
  }
}
