package com.strudent.grade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.strudent.grade.api.StudentController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.strudent.grade"},
    basePackageClasses = {StudentController.class})
public class StudentGradeApplication {

  public static void main(String[] args) {
    SpringApplication.run(StudentGradeApplication.class, args);
  }

}
