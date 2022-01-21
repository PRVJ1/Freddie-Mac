package com.strudent.grade.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CustomStudentAPIException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private Integer statusCode;
  private String message;

}
