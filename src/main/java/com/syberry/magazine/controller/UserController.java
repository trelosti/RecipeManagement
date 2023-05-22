package com.syberry.magazine.controller;

import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.dto.UserUpdatingDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.PhotoService;
import com.syberry.magazine.service.UserService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * A controller class for User entity.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final PhotoService photoService;
  private final UserRepository userRepository;

  private static final String USER_PHOTO_CHANGED = "The user photo was changed.";

  /**
   * This method is responsible for finding a specific user by id.
   *
   * @param id the id of a specific user
   * @return UserDto
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public UserDto findUser(@PathVariable Long id) {
    log.info(String.format("GET-request: getting user with id %s", id));
    return userService.findUser(id);
  }

  /**
   * This method is responsible for finding an authenticated user's profile.
   *
   * @return the profile of the current user
   */
  @GetMapping("/profile")
  @PreAuthorize("hasRole('USER')")
  public UserDto profile() {
    log.info("GET-request: getting user profile");
    return userService.profile();
  }

  /**
   * This method is responsible for updating an authenticated user.
   */
  @PutMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<UserDto> update(@RequestBody @Valid UserUpdatingDto userUpdatingDto) {
    log.info("PUT-request: update user");
    return createResponse(userService.update(userUpdatingDto));
  }

  private ResponseEntity<UserDto> createResponse(SignInDto signInDto) {
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, signInDto.getAccessTokenCookie())
        .header(HttpHeaders.SET_COOKIE, signInDto.getRefreshTokenCookie())
        .body(signInDto.getUser());
  }

  /**
   * This method is responsible for uploading a photo for user.
   *
   * @param file The photo file to be uploaded
   * @return PhotoDto with photo info
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  @PostMapping(value = "/add-photo", consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
  @PreAuthorize("hasRole('USER')")
  public PhotoDto uploadUserImage(@RequestParam("file") MultipartFile file) throws IOException {
    return photoService.saveUserPhoto(file);
  }

  /**
   * This method is responsible for changing a photo for user.
   *
   * @param file The photo file to be uploaded
   * @return String message
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  @PostMapping(value = "/change-photo", consumes = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
  @ResponseBody
  @PreAuthorize("hasRole('USER')")
  public String changeUserPhoto(@RequestParam("file") MultipartFile file) throws IOException {
    photoService.changeUserPhoto(file);
    return USER_PHOTO_CHANGED;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasRole('USER')")
  public void deleteRecipe(@PathVariable Long id) {
    log.info(String.format("DELETE-request: delete recipe with id: %s", id));
    userRepository.deleteById(id);
  }
}
