package com.strudent.grade.exception.response;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResposne {
  private Integer statusCode;
  private String error;
  private String path;
  private String methodName;
  private String ControllerName;
  private String timeStamp;

}
