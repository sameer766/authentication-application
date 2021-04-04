package com.sameer.authenticationapplication.exceptions;

import com.sameer.authenticationapplication.response.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionsHandler {

  @ExceptionHandler(value = {UserServiceException.class})
  public ResponseEntity<?> handleUserException(WebRequest webRequest,
                                               UserServiceException userServiceException) {
    return new ResponseEntity<>(new ErrorMessage(new Date(), userServiceException.getMessage()),
                                new HttpHeaders(),
                                HttpStatus.INTERNAL_SERVER_ERROR);

  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<?> handleGenericException(WebRequest webRequest,
                                                  Exception exception) {
     return new ResponseEntity<>(new ErrorMessage(new Date(), exception.getMessage()),
                                new HttpHeaders(),
                                HttpStatus.INTERNAL_SERVER_ERROR);

  }

}
