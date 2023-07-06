package com.syberry.magazine.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * A controller class for photos.
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/uploads")
@RequiredArgsConstructor
public class PhotoController {
  private static final String UPLOAD_DIR = "uploads/";

  /**
   * This method is responsible for serving photos.
   *
   * @param response HttpServletResponseObject
   * @param filename the name of the file
   * @throws IOException -- exception thrown when an I/O error occurs
   */
  @GetMapping("/{filename:.+}")
  @ResponseBody
  public void servePhoto(HttpServletResponse response, @PathVariable String filename) throws IOException {
    Resource file = new FileSystemResource(UPLOAD_DIR + filename);
    MediaType mediaType = MediaTypeFactory.getMediaType(file)
        .orElse(MediaType.APPLICATION_OCTET_STREAM);

    response.setContentType(mediaType.toString());
    InputStream inputStream = file.getInputStream();
    IOUtils.copy(inputStream, response.getOutputStream());

    response.flushBuffer();
  }
}
