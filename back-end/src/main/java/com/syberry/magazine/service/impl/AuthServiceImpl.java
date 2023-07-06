package com.syberry.magazine.service.impl;

import com.syberry.magazine.dto.RefreshTokenDto;
import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.SignInRequestDto;
import com.syberry.magazine.dto.UserCreatingDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.UserDetailsImpl;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.TokenRefreshException;
import com.syberry.magazine.service.AuthService;
import com.syberry.magazine.service.RefreshTokenService;
import com.syberry.magazine.service.TokenProcessor;
import com.syberry.magazine.service.UserService;
import com.syberry.magazine.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AuthService interface.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;
  private final TokenProcessor tokenProcessor;

  @Override
  public SignInDto signIn(SignInRequestDto signInRequestDto) {
    String login = signInRequestDto.getLogin();
    UserDto userDto = userService.findUserByLogin(login);
    Authentication authentication = setAuthentication(login,
        signInRequestDto.getPassword());
    return authenticateUser((UserDetailsImpl) authentication.getPrincipal(), userDto);
  }

  @Override
  @Transactional
  public SignInDto signUp(UserCreatingDto userCreatingDto) {
    UserDto userDto = userService.createUser(userCreatingDto);
    Authentication authentication = setAuthentication(userCreatingDto.getLogin(),
        userCreatingDto.getPassword());
    return authenticateUser((UserDetailsImpl) authentication.getPrincipal(), userDto);
  }

  @Override
  public SignInDto refresh(String refreshToken) {
    if (StringUtils.isNotBlank(refreshToken)) {
      return refreshTokenService.refreshAccessToken(refreshToken);
    } else {
      throw new TokenRefreshException(ExceptionMessage.TOKEN_EXPIRE);
    }
  }

  @Override
  public SignInDto logout() {
    Long id = SecurityUtil.getUserDetails().getId();
    refreshTokenService.deleteByUserId(id);
    ResponseCookie accessTokenCookie = tokenProcessor.getCleanJwtAccessCookie();
    ResponseCookie refreshTokenCookie = tokenProcessor.getCleanJwtRefreshCookie();
    return SignInDto.builder()
        .accessTokenCookie(accessTokenCookie.toString())
        .refreshTokenCookie(refreshTokenCookie.toString())
        .build();
  }

  private SignInDto authenticateUser(UserDetailsImpl userDetails, UserDto userDto) {
    ResponseCookie accessTokenCookie =
        tokenProcessor.generateAccessJwtCookie(userDetails.getUsername());
    RefreshTokenDto refreshTokenDto =
        refreshTokenService.createRefreshToken(userDto);
    ResponseCookie refreshTokenCookie = tokenProcessor.generateRefreshJwtCookie(
        refreshTokenDto.getToken());
    return SignInDto.builder()
        .accessTokenCookie(accessTokenCookie.toString())
        .refreshTokenCookie(refreshTokenCookie.toString())
        .user(userDto)
        .build();
  }

  private Authentication setAuthentication(String login, String password) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(login, password));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication;
  }
}
