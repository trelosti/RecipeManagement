package com.syberry.magazine.service;

import com.syberry.magazine.dto.RefreshTokenDto;
import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.UserDto;

/**
 * Token management service.
 */
public interface RefreshTokenService {

  /**
   * The method responsible for creating refresh token.
   *
   * @param userDto information about authenticated user
   * @return created refresh token
   */
  RefreshTokenDto createRefreshToken(UserDto userDto);

  /**
   * The method responsible for refreshing authenticated user's tokens.
   *
   * @param refreshToken value of refresh token
   * @return tokens and authenticated user
   */
  SignInDto refreshAccessToken(String refreshToken);

  /**
   * The method responsible for updating access and refresh tokens.
   *
   * @param userDto information about authenticated user
   * @return updated access and refresh tokens
   */
  SignInDto updateTokens(UserDto userDto);

  /**
   * The method responsible for deleting refresh token by user id.
   *
   * @param userId The id of a specific user
   */
  void deleteByUserId(Long userId);
}
