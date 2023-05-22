package com.syberry.magazine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.syberry.magazine.BackEndApplication;
import com.syberry.magazine.BaseTest;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StreamUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BackEndApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class PhotoControllerIntegrationTest extends BaseTest {
  @Test
  void servePhoto_returnsFile_whenFileExists() throws Exception {
    String filename = "test.jpg";
    Files.write(Paths.get(uploadDir + filename), testPhotoData.getBytes());

    MvcResult result = mockMvc.perform(get(servePhotoPath, filename))
        .andExpect(status().isOk())
        .andReturn();

    Resource responseFile = new ByteArrayResource(result.getResponse().getContentAsByteArray());
    byte[] responseBytes = StreamUtils.copyToByteArray(responseFile.getInputStream());
    assertEquals(new String(responseBytes), testPhotoData);

    Files.deleteIfExists(Paths.get(uploadDir + filename));
  }
}
