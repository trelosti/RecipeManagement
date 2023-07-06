package com.syberry.magazine.controller.handler;

import com.syberry.magazine.dto.error.BaseError;
import com.syberry.magazine.exception.EntityExistsException;
import com.syberry.magazine.exception.EntityNotFoundException;
import com.syberry.magazine.exception.InvalidArgumentValueException;
import com.syberry.magazine.exception.OwnerException;
import com.syberry.magazine.exception.PhotoCompressionException;
import com.syberry.magazine.exception.PhotoConflictException;
import com.syberry.magazine.exception.StorageException;
import com.syberry.magazine.exception.TokenRefreshException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A class that handles exceptions on controller side.
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
  /**
   * This method is responsible for handling EntityNotFoundException.
   *
   * @param ex the occurred EntityNotFoundException
   * @return BaseError
   */
  @ExceptionHandler({EntityNotFoundException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseError handleUserNotFoundException(EntityNotFoundException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * This method is responsible for handling EntityExistsException.
   *
   * @param ex the occurred EntityExistsException
   * @return BaseError
   */
  @ExceptionHandler({EntityExistsException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public BaseError handleUserExistsException(EntityExistsException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling TokenRefreshException.
   *
   * @param ex the occurred TokenRefreshException
   * @return BaseError
   */
  @ExceptionHandler({TokenRefreshException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleTokenRefreshException(TokenRefreshException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling OwnerException.
   *
   * @param ex the occurred OwnerException
   * @return BaseError
   */
  @ExceptionHandler({OwnerException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleOwnerException(OwnerException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling InvalidArgumentValueException.
   *
   * @param ex the occurred InvalidArgumentValueException
   * @return BaseError
   */
  @ExceptionHandler({InvalidArgumentValueException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleInvalidArgumentValueException(InvalidArgumentValueException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * This method is responsible for handling MethodArgumentNotValidException exception.
   *
   * @param ex the occurred MethodArgumentNotValidException
   * @return BaseError
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Map<String, List<String>>>
      handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, Map<String, List<String>>> errorsMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(fieldError -> {
          String fieldName = fieldError.getField();
          String errorMessage = fieldError.getDefaultMessage();

          Map<String, List<String>> fieldErrorMap = errorsMap
              .computeIfAbsent(fieldName, k -> new HashMap<>());

          List<String> errorList = fieldErrorMap
              .computeIfAbsent("messages", k -> new ArrayList<>());

          errorList.add(errorMessage);
        });

    return errorsMap;
  }

  /**
   * The method is responsible for handling PhotoConflictException.
   *
   * @param ex the occurred PhotoConflictException.
   * @return BaseError
   */
  @ExceptionHandler({PhotoConflictException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  public BaseError handlePhotoConflictException(PhotoConflictException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling PhotoCompressionException.
   *
   * @param ex the occurred PhotoCompressionException.
   * @return BaseError
   */
  @ExceptionHandler({PhotoCompressionException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public BaseError handlePhotoCompressionException(PhotoCompressionException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling StorageException.
   *
   * @param ex the occurred StorageException.
   * @return BaseError
   */
  @ExceptionHandler({StorageException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleStorageException(StorageException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling FileSizeLimitExceededException.
   *
   * @param ex FileSizeLimitExceededException
   * @return BaseError
   */
  @ExceptionHandler(FileSizeLimitExceededException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleFileSize(FileSizeLimitExceededException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }

  /**
   * The method is responsible for handling SizeLimitExceededException.
   *
   * @param ex SizeLimitExceededException
   * @return BaseError
   */
  @ExceptionHandler(SizeLimitExceededException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public BaseError handleFileSize1(SizeLimitExceededException ex) {
    BaseError responseBody = new BaseError();
    responseBody.setMessage(ex.getMessage());
    return responseBody;
  }
}
