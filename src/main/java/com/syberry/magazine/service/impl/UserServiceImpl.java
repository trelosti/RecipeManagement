package com.syberry.magazine.service.impl;

import com.syberry.magazine.converter.UserConverter;
import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.dto.UserUpdatingDto;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.enumeration.Role;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.RefreshTokenService;
import com.syberry.magazine.service.UserService;
import com.syberry.magazine.utils.SecurityUtil;
import com.syberry.magazine.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A service class for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserValidator userValidator;
  private final RefreshTokenService refreshTokenService;

  @Transactional
  @Override
  public UserDto createUser(UserCreatingDto userCreatingDto) {
    User user = UserConverter.convertToEntity(userCreatingDto);
    userValidator.validateLoginUniqueness(user.getLogin());

    user.setRole(Role.USER);

    String encodedPassword = passwordEncoder.encode(userCreatingDto.getPassword());
    user.setPassword(encodedPassword);
    User save = userRepository.save(user);
    return UserConverter.convertToDto(save);
  }

  @Override
  public UserDto findUser(long id) {
    User user = findUserByIdIfExists(id);
    return UserConverter.convertToDto(user);
  }

  @Override
  public UserDto findUserByLogin(String login) {
    User user = findUserByLoginIfExists(login);
    return UserConverter.convertToDto(user);
  }

  @Override
  public UserDto profile() {
    Long id = SecurityUtil.getUserDetails().getId();
    User user = findUserByIdIfExists(id);
    return UserConverter.convertToDto(user);
  }

  @Transactional
  @Override
  public SignInDto update(UserUpdatingDto userUpdatingDto) {
    Long id = SecurityUtil.getUserDetails().getId();
    User user = findUserByIdIfExists(id);
    updateUserFields(user, userUpdatingDto);
    UserDto userDto = UserConverter.convertToDto(user);
    return refreshTokenService.updateTokens(userDto);
  }

  private void updateUserFields(User user, UserUpdatingDto userUpdatingDto) {
    String newLogin = userUpdatingDto.getLogin();
    if (!user.getLogin().equals(newLogin)) {
      userValidator.validateLoginUniqueness(newLogin);
    }
    user.setLogin(newLogin);
    user.setFirstName(userUpdatingDto.getFirstName());
    user.setLastName(userUpdatingDto.getLastName());
  }

  private User findUserByIdIfExists(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String
            .format(ExceptionMessage.USER_ID_NOT_FOUND, id)));
  }

  private User findUserByLoginIfExists(String login) {
    return userRepository.findByLogin(login).orElseThrow(
        () -> new EntityNotFoundException(ExceptionMessage.INCORRECT_LOGIN));
  }
}
