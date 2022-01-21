package com.strudent.grade.advice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import com.strudent.grade.exception.CustomStudentAPIException;
import com.strudent.grade.exception.response.ErrorResposne;

@org.springframework.web.bind.annotation.ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice {

  public static final DateFormat getDateTimeFormat() {
    return new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
  }

  @ExceptionHandler(CustomStudentAPIException.class)
  public ResponseEntity<ErrorResposne> handleEmptyInputException(CustomStudentAPIException ex,
      HandlerMethod handlerMethod, HttpServletRequest req) {
    ErrorResposne error = new ErrorResposne();
    error.setStatusCode(ex.getStatusCode());
    error.setError(ex.getLocalizedMessage());
    error.setControllerName(String.valueOf(handlerMethod.getMethod().getDeclaringClass()));
    error.setMethodName(handlerMethod.getMethod().getName());
    error.setTimeStamp(getDateTimeFormat().format(System.currentTimeMillis()));
    error.setPath(req.getRequestURL().toString());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

}
