package com.syberry.magazine.service;

import com.syberry.magazine.dto.PagedResponse;
import com.syberry.magazine.dto.RecipeCreatingDto;
import com.syberry.magazine.dto.RecipeDetailsDto;
import com.syberry.magazine.dto.RecipeDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * An interface for managing recipes.
 */
public interface RecipeService {

  /**
   * This method is responsible for creating a recipe.
   *
   * @param recipeCreatingDto the data of the recipe to create
   * @param recipePhoto a photo for the recipe
   * @param stepPhotos photos for steps
   * @return created recipe
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  RecipeDetailsDto createRecipe(RecipeCreatingDto recipeCreatingDto,
                                MultipartFile recipePhoto,
                                MultipartFile[] stepPhotos) throws IOException;

  /**
   * This method is responsible for add/delete recipes to/from favorites.
   *
   * @param id the id of a specific recipe
   * @return information about recipe
   */
  RecipeDetailsDto toggleFavoriteRecipe(Long id);

  /**
   * This method is responsible for finding a recipe by id.
   *
   * @param id the id of a specific recipe
   * @return information about recipe
   */
  RecipeDetailsDto findRecipe(Long id);

  /**
   * This method responsible for finding page with recipes.
   *
   * @param page page number
   * @param size page size
   * @return the specific page with recipes
   */
  PagedResponse<RecipeDto> findRecipes(int page, int size);

  /**
   *  This method is responsible for finding page with recipes by author id.
   *
   * @param id the id of the author
   * @param page page number
   * @param size page size
   * @return the specified page with recipes
   */
  PagedResponse<RecipeDto> findRecipesByUserId(Long id, int page, int size);

  /**
   * This method is responsible for finding page with saved recipes.
   *
   * @param page page number
   * @param size page size
   * @return the specified page with saved recipes
   */
  PagedResponse<RecipeDto> findSavedRecipes(int page, int size);

  /**
   * This method is responsible for updating a recipe.
   *
   * @param recipeCreatingDto the data of the recipe to update
   * @param recipePhoto a photo for the recipe
   * @param stepPhotos photos for steps
   * @return the updated recipe
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  RecipeDetailsDto updateRecipe(Long id,
                                RecipeCreatingDto recipeCreatingDto,
                                MultipartFile recipePhoto,
                                MultipartFile[] stepPhotos) throws IOException;

  /**
   * This method is responsible for deleting the recipe.
   *
   * @param id the id of the recipe
   */
  void deleteRecipe(Long id);
}
