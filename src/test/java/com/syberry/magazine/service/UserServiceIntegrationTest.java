package com.syberry.magazine.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.syberry.magazine.BackEndApplication;
import com.syberry.magazine.BaseTest;
import com.syberry.magazine.converter.UserConverter;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.EntityExistsException;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BackEndApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class UserServiceIntegrationTest extends BaseTest {
  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    UserCreatingDto userCreatingDto = createUserCreatingDto();

    userService.createUser(userCreatingDto);
  }

  @Test
  void createUserTest_shouldCreateUser() {
    UserCreatingDto userCreatingDto = createUserCreatingDto();
    userCreatingDto.setLogin(firstName);
    UserDto actualResult = userService.createUser(userCreatingDto);

    assertThat(userRepository.count() == 1);

    UserDto expectedResult = findExpectedResult(id);
    expectedResult.setLogin(firstName);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void createUserTest_shouldThrowEntityExistsException() {
    UserCreatingDto userCreatingDto = createUserCreatingDto();

    assertThrows(EntityExistsException.class, () -> userService.createUser(userCreatingDto));
  }

  @Test
  void findUserTest_shouldFindUser() {
    UserDto actualResult = userService.findUser(id);
    UserDto expectedResult = findExpectedResult(id);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void findUserTest_shouldThrowEntityNotFoundException() {
    assertThrows(EntityNotFoundException.class, () -> userService.findUser(2L));
  }

  private UserDto findExpectedResult(Long id) {
    Optional<User> user = userRepository.findById(id);

    assertTrue(user.isPresent());

    return UserConverter.convertToDto(user.get());
  }
}
