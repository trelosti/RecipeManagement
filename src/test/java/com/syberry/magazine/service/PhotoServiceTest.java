package com.syberry.magazine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.syberry.magazine.BaseTest;
import com.syberry.magazine.converter.PhotoConverter;
import com.syberry.magazine.dto.photo.PhotoCreatingDto;
import com.syberry.magazine.dto.photo.PhotoDto;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.repository.PhotoRepository;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.impl.PhotoServiceImpl;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PhotoServiceTest extends BaseTest {
  @InjectMocks
  private PhotoServiceImpl photoService;

  @Mock
  private PhotoRepository photoRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private StorageService storageService;

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  private User user;

  @BeforeEach
  public void init() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(createUserDetails());

    user = createUser();
  }

  @Test
  public void createUserPhotoTest_should_createPhoto() throws IOException {
    when(userRepository.findById(id)).thenReturn(Optional.of(user));
    when(photoRepository.save(createPhoto())).thenReturn(createPhoto());

    MockMultipartFile multipartFile = createMultipartFile();

    when(storageService.store(multipartFile)).thenReturn(createBaseFile());

    PhotoDto expectedResult = createPhotoDto();

    PhotoDto actualResult = photoService.saveUserPhoto(multipartFile);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void createUserPhotoTest_should_throwError_whenEntityNotFound() throws IOException {
    PhotoCreatingDto photoCreatingDto = createPhotoCreationDto();

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> photoService
        .createUserPhoto(photoCreatingDto));
  }

  @Test
  public void changePhotoTest_should_changePhoto() throws IOException {
    Photo photo = createPhoto();

    when(userRepository.findById(id)).thenReturn(Optional.of(user));
    when(photoRepository.findByName(photo.getName())).thenReturn(Optional.of(photo));
    when(photoRepository.save(createPhoto())).thenReturn(createPhoto());

    PhotoCreatingDto photoCreatingDto = createPhotoCreationDto();

    photoService.createUserPhoto(photoCreatingDto);

    MockMultipartFile multipartFile = createMultipartFile();

    when(storageService.store(multipartFile)).thenReturn(createBaseFile());

    photoService.changeUserPhoto(multipartFile);

    verify(photoRepository, times(1)).findByName(photo.getName());

    PhotoDto expectedResult = createPhotoDto();
    PhotoDto actualResult = PhotoConverter
        .convertToDto(photoRepository.findByName(testPhotoName).get());
    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void updateUserPhotoTest_should_throwError_whenEntityNotFound()  {
    PhotoCreatingDto photoCreatingDto = createPhotoCreationDto();

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> photoService
        .updateUserPhoto(photoCreatingDto));
  }
}
