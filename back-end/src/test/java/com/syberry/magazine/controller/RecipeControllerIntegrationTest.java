package com.syberry.magazine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.syberry.magazine.BackEndApplication;
import com.syberry.magazine.BaseTest;
import com.syberry.magazine.converter.IngredientConverter;
import com.syberry.magazine.converter.PagedResponseConverter;
import com.syberry.magazine.converter.RecipeConverter;
import com.syberry.magazine.dto.PagedResponse;
import com.syberry.magazine.dto.RecipeCreatingDto;
import com.syberry.magazine.dto.RecipeDetailsDto;
import com.syberry.magazine.dto.RecipeDto;
import com.syberry.magazine.dto.SignInRequestDto;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.entity.Ingredient;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.Step;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.service.AuthService;
import com.syberry.magazine.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BackEndApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class RecipeControllerIntegrationTest extends BaseTest {
  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;
  private String signInRequest;
  private Recipe recipe;
  private User user;
  private SignInRequestDto signInRequestDto;

  @BeforeEach
  void setUp() throws Exception {
    UserCreatingDto userCreatingDto = createUserCreatingDto();
    userService.createUser(userCreatingDto);

    signInRequest = readJsonFile("json/sign-in-request.json");
    recipe = createExistingRecipe();
    user = createUser();
    signInRequestDto = createSignInRequestDto();
  }

  @Test
  @WithMockUser
  @Transactional
  void createRecipeTest_should_CreateRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    assertEquals(defaultRecipesCount, recipeRepository.count());

    MockMultipartFile recipePhoto = createRecipeMultipartFile();
    MockMultipartFile[] stepPhotos = createStepsMultipartFile();

    String createRecipeJson = readJsonFile("json/create-recipe.json");

    MockMultipartFile recipeCreatingDto = new MockMultipartFile("recipeCreatingDto", "",
        MediaType.APPLICATION_JSON_VALUE, createRecipeJson.getBytes());

    MvcResult mvcCreateRecipeResult = mockMvc.perform(MockMvcRequestBuilders.multipart(recipesPath)
            .file(recipePhoto)
            .file(stepPhotos[0])
            .file(recipeCreatingDto)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies())
        )
        .andExpect(status().isOk())
        .andReturn();

    RecipeDetailsDto actualResult =
        readValue(mvcCreateRecipeResult.getResponse().getContentAsString(),
            new TypeReference<RecipeDetailsDto>() {
            });

    Long id = actualResult.getId();
    RecipeDetailsDto expectedResult = findExpectedRecipeDetails(id);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void createRecipeTest_should_ThrowError_WhenCreateRecipeWithoutBody() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);
    MvcResult mvcCreateRecipeResult = mockMvc.perform(MockMvcRequestBuilders.multipart(recipesPath)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies())
        )
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  void createRecipeTest_should_ThrowError_WhenCreateRecipeWithInvalidFields() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    assertEquals(defaultRecipesCount, recipeRepository.count());

    MockMultipartFile recipePhoto = createRecipeMultipartFile();
    MockMultipartFile[] stepPhotos = createStepsMultipartFile();

    String createRecipeJson = readJsonFile("json/invalid-fields-create-recipe.json");

    MockMultipartFile recipeCreatingDto = new MockMultipartFile("recipeCreatingDto", "",
        MediaType.APPLICATION_JSON_VALUE, createRecipeJson.getBytes());

    MvcResult mvcCreateRecipeResult = mockMvc.perform(MockMvcRequestBuilders.multipart(recipesPath)
            .file(recipePhoto)
            .file(stepPhotos[0])
            .file(recipeCreatingDto)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies())
        )
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  @WithMockUser
  @Transactional
  void toggleFavoriteRecipeTest_should_ToggleFavoriteRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    Recipe expectedRecipe = recipeRepository.save(recipe);

    MvcResult mvcToggleFavoriteRecipeResult =
        mockMvc.perform(put(String.format(favoriteRecipePath, id))
                .cookie(mvcSignInResult.getResponse().getCookies()))
            .andExpect(status().isOk())
            .andReturn();

    authService.signIn(signInRequestDto);

    RecipeDetailsDto expectedResult = RecipeConverter.convertToDetailsDto(expectedRecipe);
    RecipeDetailsDto actualResult =
        readValue(mvcToggleFavoriteRecipeResult.getResponse().getContentAsString(),
            new TypeReference<RecipeDetailsDto>() {
            });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @WithMockUser
  void toggleFavoriteRecipeTest_should_ThrowError_WhenToggleUnExistingRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    mockMvc.perform(put(String.format(favoriteRecipePath, id))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void findRecipeTest_should_FindRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    Recipe expectedRecipe = recipeRepository.save(recipe);

    MvcResult mvcFindRecipeResult =
        mockMvc.perform(get(String.format(specifiedRecipePath, id))
                .cookie(mvcSignInResult.getResponse().getCookies()))
            .andExpect(status().isOk())
            .andReturn();

    authService.signIn(signInRequestDto);

    RecipeDetailsDto expectedResult = RecipeConverter.convertToDetailsDto(expectedRecipe);
    RecipeDetailsDto actualResult =
        readValue(mvcFindRecipeResult.getResponse().getContentAsString(),
            new TypeReference<RecipeDetailsDto>() {
            });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void findRecipeTest_should_ThrowError_WhenSearchingUnExistingRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    mockMvc.perform(get(String.format(specifiedRecipePath, id))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void findRecipesTest_should_FindRecipes() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(recipesCount);

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(recipesPath)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    authService.signIn(signInRequestDto);

    Page<Recipe> expectedPage = recipeRepository.findAllByOrderByUpdatedAtDesc(
        PageRequest.of(page, defaultSize));
    List<RecipeDto> expectedContent = expectedPage.getContent().stream()
        .map(RecipeConverter::convertToDto)
        .toList();

    PagedResponse<RecipeDto> expectedResult =
        PagedResponseConverter.convertToResponse(expectedPage, expectedContent);
    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @Transactional
  void findRecipesTest_should_FindRecipesWithSpecifiedParams() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(recipesCount);

    authService.signIn(signInRequestDto);

    Page<Recipe> expectedPage = recipeRepository.findAllByOrderByUpdatedAtDesc(
        PageRequest.of(page, size));
    List<RecipeDto> expectedContent = expectedPage.getContent().stream()
        .map(RecipeConverter::convertToDto).toList();

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(recipesPath)
            .param(pageParam, String.valueOf(page))
            .param(sizeParam, String.valueOf(size))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });
    PagedResponse<RecipeDto> expectedResult = PagedResponseConverter.convertToResponse(
        expectedPage, expectedContent);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @Transactional
  void findRecipesByUserTest_should_FindRecipes() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(defaultSize);

    authService.signIn(signInRequestDto);

    Page<Recipe> expectedPage = recipeRepository.findAllByAuthorIdOrderByUpdatedAtDesc(id,
        PageRequest.of(page, defaultSize));
    List<RecipeDto> expectedContent = expectedPage.getContent().stream()
        .map(RecipeConverter::convertToDto).toList();

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(String.format(userRecipePath, id))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    PagedResponse<RecipeDto> expectedResult = PagedResponseConverter.convertToResponse(
        expectedPage, expectedContent);
    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @Transactional
  void findRecipesByUserTest_should_FindRecipesWithSpecifiedParams() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(recipesCount);

    Page<Recipe> expectedPage = recipeRepository.findAllByAuthorIdOrderByUpdatedAtDesc(id,
        PageRequest.of(page, specifiedSize));
    authService.signIn(signInRequestDto);
    List<RecipeDto> expectedContent = expectedPage.getContent().stream()
        .map(RecipeConverter::convertToDto).toList();

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(String.format(userRecipePath, id))
            .param(pageParam, String.valueOf(page))
            .param(sizeParam, String.valueOf(specifiedSize))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    PagedResponse<RecipeDto> expectedResult = PagedResponseConverter.convertToResponse(
        expectedPage, expectedContent);
    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void findRecipesByUserTest_should_ThrowError_WhenSearchingRecipesByUnExistingUserId()
      throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    mockMvc.perform(get(String.format(userRecipePath,
            unExistingId))
            .param(pageParam, String.valueOf(page))
            .param(sizeParam, String.valueOf(specifiedSize))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  @Transactional
  void findSavedRecipesTest_should_FindSavedRecipes() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(recipesCount);

    List<Recipe> recipes = recipeRepository.findAll();
    recipes.stream()
        .limit(2)
        .forEach(recipe -> recipe.toggleSave(user));

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(favoritesRecipePath)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    authService.signIn(signInRequestDto);

    Page<Recipe> expectedPages = recipeRepository.findAllBySavesIdOrderByUpdatedAtDesc(
        id, PageRequest.of(page, defaultSize));
    List<RecipeDto> expectedContent = expectedPages.getContent().stream()
        .map(RecipeConverter::convertToDto).toList();

    PagedResponse<RecipeDto> expectedResult =
        PagedResponseConverter.convertToResponse(expectedPages, expectedContent);
    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @Transactional
  void findSavedRecipesTest_should_FindSavedRecipesWithSpecifiedParams() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    generateRecipes(recipesCount);

    List<Recipe> recipes = recipeRepository.findAll();
    recipes.stream()
        .limit(5)
        .forEach(recipe -> recipe.toggleSave(user));

    MvcResult mvcFindRecipesResult = mockMvc.perform(get(favoritesRecipePath)
            .param(pageParam, String.valueOf(secondPage))
            .param(sizeParam, String.valueOf(specifiedSize))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    authService.signIn(signInRequestDto);

    Page<Recipe> expectedPages = recipeRepository.findAllBySavesIdOrderByUpdatedAtDesc(
        id, PageRequest.of(secondPage, specifiedSize));
    List<RecipeDto> expectedContent = expectedPages.getContent().stream()
        .map(RecipeConverter::convertToDto).toList();

    PagedResponse<RecipeDto> expectedResult =
        PagedResponseConverter.convertToResponse(expectedPages, expectedContent);
    PagedResponse<RecipeDto> actualResult = readValue(
        mvcFindRecipesResult.getResponse().getContentAsString(),
        new TypeReference<PagedResponse<RecipeDto>>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  @WithMockUser
  @Transactional
  void updateRecipeTest_should_UpdateRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    recipeRepository.save(recipe);

    MockMultipartFile recipePhoto = createRecipeMultipartFile();
    MockMultipartFile[] stepPhotos = createStepsMultipartFile();

    String updateRecipe = readJsonFile("json/update-recipe.json");

    MockMultipartFile recipeCreatingDto = new MockMultipartFile("recipeCreatingDto", "",
        MediaType.APPLICATION_JSON_VALUE, updateRecipe.getBytes());

    MockMultipartHttpServletRequestBuilder builder =
        createPutMultipartBuilder(specifiedRecipePath, id);

    MvcResult mvcUpdateRecipeResult = mockMvc.perform(builder
            .file(recipePhoto)
            .file(stepPhotos[0])
            .file(recipeCreatingDto)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    authService.signIn(signInRequestDto);

    RecipeDetailsDto expectedResult = findExpectedRecipeDetails(id);
    RecipeDetailsDto actualResult = readValue(
        mvcUpdateRecipeResult.getResponse().getContentAsString(),
        new TypeReference<RecipeDetailsDto>() {
        });

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void updateRecipeTest_should_ThrowError_WhenUpdatingUnExistingRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    recipeRepository.save(recipe);

    MockMultipartFile recipePhoto = createRecipeMultipartFile();
    MockMultipartFile[] stepPhotos = createStepsMultipartFile();

    String updateRecipe = readJsonFile("json/update-recipe.json");

    MockMultipartFile recipeCreatingDto = new MockMultipartFile(
        "recipeCreatingDto",
        "",
        MediaType.APPLICATION_JSON_VALUE,
        updateRecipe.getBytes());

    MockMultipartHttpServletRequestBuilder builder =
        createPutMultipartBuilder(specifiedRecipePath, unExistingId);

    mockMvc.perform(builder
            .file(recipePhoto)
            .file(stepPhotos[0])
            .file(recipeCreatingDto)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateRecipeTest_should_ThrowError_WhenUpdateRecipeWithInvalidFields() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);
    String updateRecipeRequest = readJsonFile("json/invalid-fields-create-recipe.json");

    MockMultipartFile recipePhoto = createRecipeMultipartFile();
    MockMultipartFile[] stepPhotos = createStepsMultipartFile();

    MockMultipartFile recipeCreatingDto = new MockMultipartFile("recipeCreatingDto", "",
        MediaType.APPLICATION_JSON_VALUE, updateRecipeRequest.getBytes());

    MockMultipartHttpServletRequestBuilder builder =
        createPutMultipartBuilder(specifiedRecipePath, id);

    mockMvc.perform(builder
            .file(recipePhoto)
            .file(stepPhotos[0])
            .file(recipeCreatingDto)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteRecipeTest_should_DeleteRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    recipeRepository.save(recipe);

    mockMvc.perform(delete(String.format(specifiedRecipePath, id))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteRecipeTest_should_ThrowError_WhenDeletingUnExistingRecipe() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    mockMvc.perform(delete(String.format(specifiedRecipePath, unExistingId))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isNotFound());
  }

  private void generateRecipes(int recipeCount) {
    for (int i = 0; i < recipeCount; i++) {
      RecipeCreatingDto recipeCreatingDto = createRecipeCreatingDto();
      Recipe newRecipe = RecipeConverter.convertToEntity(recipeCreatingDto);

      newRecipe.setPhoto(createPhoto());
      newRecipe.setAuthor(user);
      newRecipe.setSaves(new ArrayList<>());

      List<Step> steps = recipeCreatingDto.getSteps().stream()
          .map(Step::new)
          .toList();
      List<Ingredient> ingredients = recipeCreatingDto.getIngredients().stream()
          .map(IngredientConverter::convertToEntity)
          .toList();

      Recipe savedRecipe = recipeRepository.save(newRecipe);

      savedRecipe.addSteps(steps);
      savedRecipe.addIngredients(ingredients);
    }
  }

  private RecipeDetailsDto findExpectedRecipeDetails(Long recipeId) {
    Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

    assertTrue(optionalRecipe.isPresent());

    Recipe recipe = optionalRecipe.get();

    return RecipeConverter.convertToDetailsDto(recipe);
  }
}
