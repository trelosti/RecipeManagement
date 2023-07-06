package com.syberry.magazine.service.impl;

import com.syberry.magazine.converter.RefreshTokenConverter;
import com.syberry.magazine.converter.UserConverter;
import com.syberry.magazine.dto.RefreshTokenDto;
import com.syberry.magazine.dto.SignInDto;
import com.syberry.magazine.dto.UserDto;
import com.syberry.magazine.entity.RefreshToken;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.ExceptionMessage;
import com.syberry.magazine.exception.TokenRefreshException;
import com.syberry.magazine.repository.RefreshTokenRepository;
import com.syberry.magazine.service.RefreshTokenService;
import com.syberry.magazine.service.TokenProcessor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of token management service.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
  @Value("${app.security.refresh-token-expiration-time-day}")
  private long refreshTokenExpirationTimeDay;
  private final RefreshTokenRepository refreshTokenRepository;
  private final TokenProcessor tokenProcessor;

  @Override
  @Transactional
  public RefreshTokenDto createRefreshToken(UserDto userDto) {
    Long userId = userDto.getId();
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserId(userId);
    RefreshToken refreshToken = refreshTokenOptional.orElseGet(() -> RefreshToken.builder()
        .user(UserConverter.convertToEntity(userDto))
        .build());

    Instant expiryDate = Instant.now().plus(refreshTokenExpirationTimeDay, ChronoUnit.DAYS);
    refreshToken.setExpiryDate(expiryDate);
    refreshToken.setToken(UUID.randomUUID().toString());

    RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
    return RefreshTokenConverter.convertToDto(savedRefreshToken);
  }

  @Override
  @Transactional
  public SignInDto refreshAccessToken(String refreshToken) {
    RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
        .orElseThrow(() -> new TokenRefreshException(ExceptionMessage.TOKEN_NOT_FOUND));
    Instant expiryDate = token.getExpiryDate();

    if (expiryDate.isBefore(Instant.now())) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(ExceptionMessage.TOKEN_EXPIRE);
    }

    User user = token.getUser();

    ResponseCookie accessTokenCookie = tokenProcessor.generateAccessJwtCookie(user.getLogin());
    ResponseCookie refreshTokenCookie = tokenProcessor.generateRefreshJwtCookie(
        createRefreshToken(UserConverter.convertToDto(user)).getToken());
    return SignInDto.builder()
        .accessTokenCookie(accessTokenCookie.toString())
        .refreshTokenCookie(refreshTokenCookie.toString())
        .user(UserConverter.convertToDto(user))
        .build();
  }

  @Override
  @Transactional
  public SignInDto updateTokens(UserDto userDto) {
    tokenProcessor.updateUserCredentialsInContext(userDto.getLogin());
    deleteByUserId(userDto.getId());

    RefreshTokenDto refreshTokenDto = createRefreshToken(userDto);
    ResponseCookie accessTokenCookie = tokenProcessor.generateAccessJwtCookie(userDto.getLogin());
    ResponseCookie refreshTokenCookie = tokenProcessor.generateRefreshJwtCookie(
        refreshTokenDto.getToken());
    return SignInDto.builder()
        .accessTokenCookie(accessTokenCookie.toString())
        .refreshTokenCookie(refreshTokenCookie.toString())
        .user(userDto)
        .build();
  }

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    refreshTokenRepository.deleteRefreshTokenByUserId(userId);
  }
}
