package com.syberry.magazine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.syberry.magazine.BaseTest;
import com.syberry.magazine.dto.file.BaseFile;
import com.syberry.magazine.entity.Photo;
import com.syberry.magazine.entity.User;
import com.syberry.magazine.exception.StorageException;
import com.syberry.magazine.repository.PhotoRepository;
import com.syberry.magazine.repository.UserRepository;
import com.syberry.magazine.service.impl.StorageServiceImpl;
import com.syberry.magazine.utils.file.PhotoConstant;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StorageServiceTest extends BaseTest {
  @InjectMocks
  private StorageServiceImpl storageService;

  @Mock
  private PhotoRepository photoRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  private MockHttpServletRequest mockRequest;

  @Mock
  private Path mockLocation;

  private User user;

  private Path uploadDir;

  @BeforeEach
  public void init() {
    uploadDir = Paths.get(PhotoConstant.UPLOAD_DIR);;

    createUploadDirectory(uploadDir);

    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(createUserDetails());

    user = createUser();

    mockRequest = new MockHttpServletRequest();
    mockRequest.setContextPath(testContextPath);

    ServletRequestAttributes attrs = new ServletRequestAttributes(mockRequest);

    RequestContextHolder.setRequestAttributes(attrs);
  }

  @Test
  void storeTest_should_returnBaseFile_whenFileIsNotEmpty() {
    MultipartFile file = new MockMultipartFile(testPhotoName, testPhotoName,
        testPhotoPath, testPhotoData.getBytes());

    BaseFile baseFile = storageService.store(file);

    assertEquals(PhotoConstant.IMG_EXTENSION, baseFile.getName()
        .substring(baseFile.getName().lastIndexOf(".")));
  }

  @Test
  void storeTest_shouldThrowStorageException_whenFileIsEmpty()  {
    MultipartFile file = new MockMultipartFile(testPhotoName, testPhotoName,
        testPhotoPath, new byte[0]);

    assertThrows(StorageException.class, () -> storageService.store(file));
  }

  @Test
  void deleteUserPhotoTest_should_deleteFile_whenUserPhotoExists() throws IOException {
    storeFile();
    Photo photo = createPhoto();
    user.setUserPhoto(photo);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    Path filePath = Paths.get(PhotoConstant.UPLOAD_DIR, photo.getName());

    storageService.deleteUserPhoto();

    assertFalse(Files.exists(filePath));
    verify(userRepository, times(1)).findById(id);
  }

  @Test
  void initTest_should_createDirectoryAndCopyFiles_whenPhotoListIsNotEmpty() throws Exception {
    List<Photo> photoList = new ArrayList<>();

    Photo photo = createPhoto();
    photoList.add(photo);

    when(photoRepository.findAll()).thenReturn(photoList);

    storageService.init();

    assertTrue(Files.list(uploadDir).findAny().isPresent());
    verify(photoRepository, times(1)).findAll();
  }

  @Test
  void initTest_should_throwNullPointerException_ifNoDirectory() throws Exception {
    List<Photo> photoList = new ArrayList<>();

    Photo photo = createPhoto();
    photoList.add(photo);

    when(photoRepository.findAll()).thenReturn(photoList);

    storageService.init();

    assertThrows(NullPointerException.class, () -> Files.list(mockLocation));
  }
}
