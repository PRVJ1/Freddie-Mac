package com.strudent.grade.api;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strudent.grade.constants.ExceptionConstants;
import com.strudent.grade.exception.CustomStudentAPIException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/student/grade")
public class StudentController {


  @GetMapping("/find")
  @ApiOperation(
      value = "Find the current grade of student given their year of high school graduation.",
      response = Integer.class)
  public ResponseEntity<Integer> findCurrentGrade(
      @RequestParam("year") @ApiParam(example = "2022", required = true) Integer givenYear) {
    if (Optional.ofNullable(givenYear).isPresent()
        && Optional.ofNullable(givenYear).orElse(0) != 0) {
      return getResponse.apply(givenYear);
    } else {
      throw new CustomStudentAPIException(ExceptionConstants.EMPTY_OR_ZERO_CODE,
          ExceptionConstants.EMPTY_OR_ZERO_MSG);
    }
  }

  private final Function<Integer, ResponseEntity<Integer>> getResponse = givenYear -> {
    Integer currentYear = LocalDateTime.now().getYear();
    if (givenYear < currentYear) {
      return new ResponseEntity<>(99, HttpStatus.OK);
    } else if (givenYear > (currentYear + 12)) {
      return new ResponseEntity<>(0, HttpStatus.OK);
    } else {
      return new ResponseEntity<>((12 - (givenYear - currentYear)), HttpStatus.OK);
    }
  };


}
