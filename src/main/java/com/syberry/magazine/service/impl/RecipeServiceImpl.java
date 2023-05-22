package com.syberry.magazine.service.impl;

import com.syberry.magazine.converter.IngredientConverter;
import com.syberry.magazine.converter.PagedResponseConverter;
import com.syberry.magazine.converter.RecipeConverter;
import com.syberry.magazine.converter.UserConverter;
import com.syberry.magazine.dto.PagedResponse;
import com.syberry.magazine.dto.RecipeCreatingDto;
import com.syberry.magazine.dto.RecipeDetailsDto;
import com.syberry.magazine.dto.RecipeDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.Ingredient;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.Step;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.entity.UserDetailsImpl;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.OwnerException;
import com.syberry.magazine.exception.PhotoConflictException;
import com.syberry.magazine.repository.PhotoRepository;
import com.syberry.magazine.repository.RecipeRepository;
import com.syberry.magazine.service.PhotoService;
import com.syberry.magazine.service.RecipeService;
import com.syberry.magazine.service.UserService;
import com.syberry.magazine.utils.SecurityUtil;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * A class for managing recipes.
 */
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
  private final RecipeRepository recipeRepository;
  private final PhotoRepository photoRepository;
  private final UserService userService;
  private final PhotoService photoService;

  @Transactional
  @Override
  public RecipeDetailsDto createRecipe(RecipeCreatingDto recipeCreatingDto,
                                       MultipartFile recipePhoto,
                                       MultipartFile[] stepPhotos) throws IOException {
    User user = findCurrentUser();
    Recipe recipe = RecipeConverter.convertToEntity(recipeCreatingDto);

    photoService.saveRecipePhoto(recipe, recipePhoto);

    recipe.setAuthor(user);
    addIngredientsAndSteps(recipe, recipeCreatingDto);

    return getRecipeDetailsDto(stepPhotos, recipe);
  }

  private RecipeDetailsDto getRecipeDetailsDto(MultipartFile[] stepPhotos, Recipe recipe) {
    List<Step> steps = recipe.getSteps();

    List<MultipartFile> photos = Arrays.asList(stepPhotos);

    Map<Step, MultipartFile> stepsToSave = distributeStepPhotos(steps, photos);

    stepsToSave.forEach((s, p) -> {
      try {
        photoService.saveStepPhoto(s, p);
      } catch (IOException e) {
        throw new PhotoConflictException(String.format(ExceptionMessage.FAILED_TO_SAVE, e.getMessage()), e);
      }
    });

    recipe = recipeRepository.save(recipe);

    return RecipeConverter.convertToDetailsDto(recipe);
  }

  @Transactional
  @Override
  public RecipeDetailsDto toggleFavoriteRecipe(Long id) {
    User user = findCurrentUser();
    Recipe recipe = findRecipeByIdIfExists(id);

    recipe.toggleSave(user);

    return RecipeConverter.convertToDetailsDto(recipe);
  }

  @Override
  public RecipeDetailsDto findRecipe(Long id) {
    Recipe recipe = findRecipeByIdIfExists(id);
    return RecipeConverter.convertToDetailsDto(recipe);
  }

  @Override
  public PagedResponse<RecipeDto> findRecipes(int page, int size) {
    Page<Recipe> recipePage =
        recipeRepository.findAllByOrderByUpdatedAtDesc(PageRequest.of(page, size));

    List<RecipeDto> content = recipePage.getContent().stream()
        .map(RecipeConverter::convertToDto)
        .toList();

    return PagedResponseConverter.convertToResponse(recipePage, content);
  }

  @Override
  public PagedResponse<RecipeDto> findRecipesByUserId(Long id, int page, int size) {
    userService.findUser(id);
    Page<Recipe> recipePage = recipeRepository.findAllByAuthorIdOrderByUpdatedAtDesc(
        id, PageRequest.of(page, size));

    List<RecipeDto> content = recipePage.getContent().stream()
        .map(RecipeConverter::convertToDto)
        .toList();

    return PagedResponseConverter.convertToResponse(recipePage, content);
  }

  @Override
  public PagedResponse<RecipeDto> findSavedRecipes(int page, int size) {
    UserDetailsImpl userDetails = SecurityUtil.getUserDetails();

    Page<Recipe> recipePage = recipeRepository.findAllBySavesIdOrderByUpdatedAtDesc(
        userDetails.getId(), PageRequest.of(page, size));

    List<RecipeDto> content = recipePage.getContent().stream()
        .map(RecipeConverter::convertToDto)
        .toList();

    return PagedResponseConverter.convertToResponse(recipePage, content);
  }

  @Transactional
  @Override
  public RecipeDetailsDto updateRecipe(Long id, RecipeCreatingDto recipeCreatingDto,
                                       MultipartFile recipePhoto,
                                       MultipartFile[] stepPhotos) throws IOException {
    Recipe recipe = findRecipeByIdIfExists(id);

    photoService.saveRecipePhoto(recipe, recipePhoto);

    checkOwner(recipe.getAuthor().getId());
    updateRecipeFields(recipe, recipeCreatingDto);

    return getRecipeDetailsDto(stepPhotos, recipe);
  }

  @Transactional
  @Override
  public void deleteRecipe(Long id) {
    Recipe recipe = findRecipeByIdIfExists(id);
    User author = recipe.getAuthor();

    checkOwner(author.getId());

    recipeRepository.deleteById(id);
  }

  private void updateRecipeFields(Recipe recipe, RecipeCreatingDto recipeCreatingDto) {
    recipe.removeAllSteps();
    recipe.removeAllIngredients();
    addIngredientsAndSteps(recipe, recipeCreatingDto);
    recipe.setRecipeName(recipeCreatingDto.getRecipeName());
    recipe.setCookTime(recipeCreatingDto.getCookTime());
    recipe.setUpdatedAt(LocalDateTime.now());
  }

  private void checkOwner(Long authorId) {
    User user = findCurrentUser();

    if (!user.getId().equals(authorId)) {
      throw new OwnerException(ExceptionMessage.OWNER_CONFLICT);
    }
  }

  private void addIngredientsAndSteps(Recipe recipe, RecipeCreatingDto recipeCreatingDto) {
    List<Step> steps = recipeCreatingDto.getSteps().stream()
        .map(Step::new)
        .toList();
    List<Ingredient> ingredients = recipeCreatingDto.getIngredients().stream()
        .map(IngredientConverter::convertToEntity)
        .toList();

    recipe.addSteps(steps);
    recipe.addIngredients(ingredients);
  }

  private Recipe findRecipeByIdIfExists(Long id) {
    return recipeRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(String.format(ExceptionMessage.RECIPE_NOT_FOUND, id)));
  }

  private User findCurrentUser() {
    UserDetailsImpl userDetails = SecurityUtil.getUserDetails();
    UserDto userDto = userService.findUser(userDetails.getId());

    String path = userDto.getUserPhotoLink();

    Optional<Photo> userPhoto = photoRepository.findByPath(path);
    Photo photo = userPhoto.orElse(null);

    return UserConverter.convertToEntity(userDto, photo);
  }

  private Map<Step, MultipartFile> distributeStepPhotos(List<Step> steps,
                                                        List<MultipartFile> photos) {
    Map<Step, MultipartFile> map = new HashMap<>();

    Iterator<Step> stepIterator = steps.iterator();
    Iterator<MultipartFile> photoIterator = photos.iterator();

    while (stepIterator.hasNext() && photoIterator.hasNext()) {
      map.put(stepIterator.next(), photoIterator.next());
    }

    return map;
  }
}
