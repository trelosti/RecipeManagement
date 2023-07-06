package com.syberry.magazine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.syberry.magazine.BackEndApplication;
import com.syberry.magazine.BaseTest;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.service.UserService;
import java.io.File;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BackEndApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserControllerIntegrationTest extends BaseTest {
  @Autowired
  private UserService userService;

  private String signInRequest;

  @BeforeEach
  void setUp() throws Exception {
    UserCreatingDto userCreatingDto = createUserCreatingDto();
    UserCreatingDto userCreatingDtoWithNewLogin = createUserCreatingDto();
    userCreatingDtoWithNewLogin.setLogin("Pepe");

    userService.createUser(userCreatingDto);
    userService.createUser(userCreatingDtoWithNewLogin);

    final File signInRequestFile = new ClassPathResource("json/sign-in-request.json").getFile();
    signInRequest = Files.readString(signInRequestFile.toPath());
  }

  @Test
  void findUserTest_shouldFindUser() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    MvcResult mvcResult = mockMvc.perform(get(String.format(specifiedUserPath, 1))
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    UserDto actualResult = readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<>() {
        }
    );
    UserDto expectedResult = findExpectedUser(id);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void findUserTest_shouldThrowError_WhenGettingUserWithUnExistingId() throws Exception {
    MvcResult mvcResult = signIn(signInRequest);

    mockMvc.perform(get(String.format(specifiedUserPath, 9999))
            .cookie(mvcResult.getResponse().getCookies()))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void profileTest_should_FindProfile() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    MvcResult mvcProfileResult = mockMvc.perform(get(profilePath)
            .cookie(mvcSignInResult.getResponse().getCookies()))
        .andExpect(status().isOk())
        .andReturn();

    UserDto actualResult = readValue(mvcProfileResult.getResponse().getContentAsString(),
        new TypeReference<UserDto>() {
        });
    Long id = actualResult.getId();

    UserDto expectedResult = findExpectedUser(id);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void profileTest_should_ThrowError_WhenUnauthorizedUserGettingProfile() throws Exception {
    mockMvc.perform(get(profilePath))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void updateTest_should_UpdateUser() throws Exception {
    final File updateRequestFile = new ClassPathResource("json/update-user.json").getFile();
    String updateRequest = Files.readString(updateRequestFile.toPath());

    MvcResult mvcSignInResult = signIn(signInRequest);

    MvcResult mvcUpdateResult = mockMvc.perform(put(usersPath)
            .cookie(mvcSignInResult.getResponse().getCookies())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateRequest))
        .andReturn();

    UserDto actualResult = readValue(mvcUpdateResult.getResponse().getContentAsString(),
        new TypeReference<UserDto>() {
        });
    Long id = actualResult.getId();

    UserDto expectedResult = findExpectedUser(id);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void updateTest_should_ThrowError_WhenUpdatingUserWithDuplicatedLogin() throws Exception {
    final File updateRequestFile = new ClassPathResource("json/duplicate-login-update-user.json")
        .getFile();
    String updateRequest = Files.readString(updateRequestFile.toPath());

    MvcResult mvcResult = signIn(signInRequest);

    mockMvc.perform(put(usersPath)
        .cookie(mvcResult.getResponse().getCookies())
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequest))
        .andExpect(status().isConflict());
  }

  @Test
  @WithMockUser
  void uploadUserImageTest_should_returnOk() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    MockMultipartFile file = new MockMultipartFile("file",
        "test-image.png", "image/png", testPhotoData.getBytes());

    MvcResult result = mockMvc.perform(multipart(addPhotoPath)
            .file(file))
        .andExpect(status().isOk())
        .andReturn();

    String responseString = result.getResponse().getContentAsString();
    PhotoDto response = objectMapper.readValue(responseString, PhotoDto.class);
    assertNotNull(response);
  }

  @Test
  @WithMockUser
  void updateUserImageTest_should_returnOk() throws Exception {
    MvcResult mvcSignInResult = signIn(signInRequest);

    MockMultipartFile file = new MockMultipartFile("file",
        "test-image.png", "image/png", testPhotoData.getBytes());

    mockMvc.perform(multipart(addPhotoPath)
            .file(file))
        .andExpect(status().isOk())
        .andReturn();

    MvcResult result = mockMvc.perform(multipart(updatePhotoPath)
            .file(file))
        .andExpect(status().isOk())
        .andReturn();

    String responseString = result.getResponse().getContentAsString();
    assertEquals(responseString, "The user photo was changed.");
  }
}
