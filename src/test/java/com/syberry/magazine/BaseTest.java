package com.syberry.magazine;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syberry.magazine.converter.UserConverter;
import com.syberry.magazine.dto.IngredientCreatingDto;
import com.syberry.magazine.dto.IngredientDto;
import com.syberry.magazine.dto.PagedResponse;
import com.syberry.magazine.dto.RecipeCreatingDto;
import com.syberry.magazine.dto.RecipeDetailsDto;
import com.syberry.magazine.dto.RecipeDto;
import com.syberry.magazine.dto.RefreshTokenDto;
import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.SignInRequestDto;
import com.syberry.magazine.dto.StepDto;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.dto.UserUpdatingDto;
import com.syberry.magazine.dto.file.BaseFile;
import com.syberry.magazine.dto.photo.PhotoCreatingDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.entity.Ingredient;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.Recipe;
import com.syberry.magazine.entity.Step;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.entity.UserDetailsImpl;
import com.syberry.magazine.enumeration.MeasureUnit;
import com.syberry.magazine.enumeration.Role;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.StorageException;
import com.syberry.magazine.repository.RecipeRepository;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.utils.file.PhotoConstant;
import com.syberry.magazine.utils.file.PhotoUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class BaseTest {
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected UserRepository userRepository;
  @Autowired
  protected RecipeRepository recipeRepository;
  protected String login = "user";
  protected String firstName = "joe";
  protected String lastName = "doe";
  protected String password = "1234_Admin";
  protected LocalDateTime localDateTime = LocalDateTime.now();
  protected Long id = 1L;
  protected String existingLogin = "existingLogin";
  protected String accessToken = "accessToken";
  protected String refreshToken = "refreshToken";
  protected String signInPath = "/auth/sign-in";
  protected String signUpPath = "/auth/sign-up";
  protected String refreshPath = "/auth/refresh";
  protected String logoutPath = "/auth/logout";
  protected String usersPath = "/users";
  protected String specifiedUserPath = usersPath + "/%s";
  protected String profilePath = usersPath + "/profile";
  protected String recipesPath = "/recipes";
  protected String specifiedRecipePath = recipesPath + "/%s";
  protected String favoritesRecipePath = recipesPath + "/favorites";
  protected String favoriteRecipePath = favoritesRecipePath + "/%s";
  protected String userRecipePath = recipesPath + "/user/%s";
  protected Long unExistingId = 9999L;
  protected Integer cookTime = 20;
  protected Integer quantity = 1;
  protected MeasureUnit measureUnit = MeasureUnit.PCS;
  protected int page = 0;
  protected int secondPage = 1;
  protected int size = 1;
  protected int specifiedSize = 4;
  protected int defaultSize = 6;
  protected int recipesCount = 12;
  protected int defaultRecipesCount = 0;
  protected String pageParam = "page";
  protected String sizeParam = "size";
  protected String stepDescription = "someDescription";
  protected String recipeName = "applePie";
  protected String ingredientName = "apple";
  protected String addPhotoPath = usersPath + "/add-photo";
  protected String updatePhotoPath = usersPath + "/change-photo";
  protected String photoLink = "none";
  protected String uploadDir = "uploads/";
  protected String servePhotoPath = "/uploads/{filename:.+}";
  protected String testPhotoData = "test data";
  protected String testPhotoName = "test.png";
  protected String testPhotoPath = "none";
  protected String testContextPath = "/api/v1";
  protected String nonExistentPhoto = "nonexistent.jpg";


  protected <T> T readValue(String value, TypeReference<T> type) throws Exception {
    return objectMapper.readValue(value, type);
  }

  protected String readJsonFile(String path) throws IOException {
    final File createRecipeFile = new ClassPathResource(path)
        .getFile();
    return Files.readString(createRecipeFile.toPath());
  }

  protected UserDto findExpectedUser(Long userId) {
    Optional<User> user = userRepository.findById(userId);

    assertTrue(user.isPresent());

    return UserConverter.convertToDto(user.get());
  }

  protected UserCreatingDto createUserCreatingDto() {
    return UserCreatingDto.builder()
        .login(login)
        .firstName(firstName)
        .lastName(lastName)
        .password(password)
        .build();
  }

  protected UserDto createUserDto() {
    return UserDto.builder()
        .id(id)
        .createdAt(localDateTime)
        .updatedAt(localDateTime)
        .login(login)
        .firstName(firstName)
        .lastName(lastName)
        .userPhotoLink(photoLink)
        .build();
  }

  protected User createUser() {
    return User.builder()
        .id(id)
        .createdAt(localDateTime)
        .updatedAt(localDateTime)
        .login(login)
        .firstName(firstName)
        .lastName(lastName)
        .password(password)
        .role(Role.USER)
        .build();
  }

  protected UserUpdatingDto createUserUpdatingDto() {
    return UserUpdatingDto.builder()
        .login(login)
        .firstName(firstName)
        .lastName(lastName)
        .build();
  }

  protected UserDetailsImpl createUserDetails() {
    return new UserDetailsImpl(createUser());
  }

  protected SignInDto createSignInDto() {
    return SignInDto.builder()
        .accessTokenCookie(createAccessTokenCookie().toString())
        .refreshTokenCookie(createRefreshTokenCookie().toString())
        .user(createUserDto())
        .build();
  }

  protected ResponseCookie createAccessTokenCookie() {
    return ResponseCookie.from(
            accessToken, accessToken)
        .build();
  }

  protected ResponseCookie createRefreshTokenCookie() {
    return ResponseCookie.from(
            refreshToken, refreshToken)
        .build();
  }

  protected RefreshTokenDto createRefreshTokenDto() {
    return RefreshTokenDto.builder()
        .id(id)
        .userDto(createUserDto())
        .token(refreshToken)
        .expiryDate(Instant.now().plus(1, ChronoUnit.DAYS))
        .build();
  }

  protected SignInDto createSignInDtoAfterLogout() {
    return SignInDto.builder()
        .accessTokenCookie(createAccessTokenCookie().toString())
        .refreshTokenCookie(createRefreshTokenCookie().toString())
        .user(null)
        .build();
  }

  protected SignInRequestDto createSignInRequestDto() {
    return SignInRequestDto.builder()
        .login(login)
        .password(password)
        .build();
  }

  protected MvcResult signIn(String content) throws Exception {
    return mockMvc.perform(post(signInPath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andReturn();
  }

  protected Recipe createNewRecipe() {
    List<User> users = new ArrayList<>();
    users.add(createUser());
    return Recipe.builder()
        .id(id)
        .recipeName(recipeName)
        .author(createUser())
        .cookTime(cookTime)
        .saves(users)
        .build();
  }

  protected Recipe createExistingRecipe() {
    List<User> users = new ArrayList<>();
    users.add(createUser());

    Photo photo = createPhoto();

    Step step = Step.builder()
        .id(id)
        .description(stepDescription)
        .photo(photo)
        .build();
    List<Step> steps = new ArrayList<>();
    steps.add(step);

    Recipe recipe = Recipe.builder()
        .id(id)
        .recipeName(recipeName)
        .author(createUser())
        .steps(steps)
        .cookTime(cookTime)
        .saves(new ArrayList<>())
        .photo(photo)
        .build();

    Ingredient ingredient = createIngredient(recipe);
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(ingredient);

    recipe.setIngredients(ingredients);
    return recipe;
  }

  protected Recipe createSavedRecipe() {
    Recipe recipe = createExistingRecipe();
    recipe.toggleSave(createUser());
    return recipe;
  }

  protected Ingredient createIngredient(Recipe recipe) {
    return Ingredient.builder()
        .id(id)
        .ingredientName(ingredientName)
        .quantity(quantity)
        .measureUnit(measureUnit)
        .build();
  }

  protected RecipeDetailsDto createRecipeDetailsDto() {
    StepDto stepDto = StepDto.builder()
        .id(id)
        .description(stepDescription)
        .build();

    IngredientDto ingredientDto = IngredientDto.builder()
        .id(id)
        .ingredientName(ingredientName)
        .measureUnit(measureUnit)
        .quantity(quantity)
        .build();

    return RecipeDetailsDto.builder()
        .id(id)
        .recipeName(recipeName)
        .author(createUserDto())
        .recipeSteps(List.of(stepDto))
        .ingredients(List.of(ingredientDto))
        .cookTime(cookTime)
        .build();
  }

  protected RecipeCreatingDto createRecipeCreatingDto() {
    IngredientCreatingDto ingredientCreatingDto = IngredientCreatingDto.builder()
        .ingredientName(ingredientName)
        .measureUnit(measureUnit.name())
        .quantity(quantity)
        .build();

    return RecipeCreatingDto.builder()
        .recipeName(recipeName)
        .steps(List.of(stepDescription))
        .ingredients(List.of(ingredientCreatingDto))
        .cookTime(cookTime)
        .build();
  }

  protected PagedResponse<RecipeDto> createRecipePagedResponse() {
    return createEmptyPagedResponse(List.of(createRecipeDto()));
  }

  protected PagedResponse<RecipeDto> createEmptyPagedResponse(List<RecipeDto> content) {
    return PagedResponse.<RecipeDto>builder()
        .size(size)
        .page(page)
        .content(content)
        .last(true)
        .totalElements(1)
        .totalPages(1)
        .build();
  }

  protected PagedResponse<RecipeDto> createDefaultPagedResponse(List<RecipeDto> content) {
    return createSpecifiedPagedResponse(page, defaultSize, 1, 1,
        true, content);
  }

  protected PagedResponse<RecipeDto> createSpecifiedPagedResponse(
      int page, int size, int totalElements, int totalPages, boolean last,
      List<RecipeDto> content) {
    return PagedResponse.<RecipeDto>builder()
        .size(size)
        .page(page)
        .content(content)
        .last(last)
        .totalElements(totalElements)
        .totalPages(totalPages)
        .build();
  }

  protected RecipeDto createRecipeDto() {
    return RecipeDto.builder()
        .id(id)
        .recipeName(recipeName)
        .build();
  }

  protected Page<Recipe> createRecipePage() {
    List<Recipe> content = new ArrayList<>();
    content.add(createExistingRecipe());
    Page<Recipe> page = new PageImpl<>(content);
    return page;
  }

  protected Photo createPhoto() {
    byte[] testData = PhotoUtil.compressPhoto(testPhotoData.getBytes());

    return Photo.builder()
        .data(testData)
        .name(testPhotoName)
        .path(testPhotoPath)
        .build();
  }

  protected PhotoDto createPhotoDto() {
    return PhotoDto.builder()
        .name(testPhotoName)
        .path(testPhotoPath)
        .build();
  }

  protected PhotoCreatingDto createPhotoCreationDto() {
    return PhotoCreatingDto.builder()
        .data(testPhotoData.getBytes())
        .name(testPhotoName)
        .path(testPhotoPath)
        .build();
  }

  protected MockMultipartFile createMultipartFile() {
    return new MockMultipartFile(testPhotoName,
        testPhotoData.getBytes());
  }

  protected BaseFile createBaseFile() {
    return BaseFile.builder()
        .name(testPhotoName)
        .path(testPhotoPath)
        .build();
  }

  protected void createUploadDirectory(Path directory) {
    try {
      Files.createDirectories(directory);

    } catch (IOException e) {
      throw new StorageException(String.format(ExceptionMessage
          .STORAGE_NOT_INITIALIZED, e.getMessage()), e);
    }
  }

  protected MockMultipartFile createRecipeMultipartFile() {
    return new MockMultipartFile("recipePhoto", "photo.jpg",
        MediaType.MULTIPART_FORM_DATA_VALUE, "image data".getBytes());
  }

  protected MockMultipartFile[] createStepsMultipartFile() {
    return new MockMultipartFile[]{
        new MockMultipartFile("stepPhotos", "step2.jpg",
            MediaType.MULTIPART_FORM_DATA_VALUE, "image data".getBytes())
    };
  }

  protected MockMultipartHttpServletRequestBuilder createPutMultipartBuilder(String path, Long id) {
    MockMultipartHttpServletRequestBuilder builder =
        MockMvcRequestBuilders.multipart(String.format(path, id));
    builder.with(new RequestPostProcessor() {
      @Override
      public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.setMethod("PUT");
        return request;
      }
    });

    return builder;
  }

  protected void storeFile() throws IOException {
    MockMultipartFile file = createMultipartFile();
    Path rootLocation = Paths.get(PhotoConstant.UPLOAD_DIR);;
    Path targetLocation = rootLocation.resolve(file.getName());

    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
  }
}
