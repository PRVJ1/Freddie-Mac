package com.strudent.grade.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.strudent.grade.api.StudentController;
import com.strudent.grade.constants.ExceptionConstants;
import com.strudent.grade.exception.CustomStudentAPIException;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
  private static final String URL = "/student/grade/find";

  private static final String INPUT_PARAM_KEY = "year";

  @Mock
  private WebApplicationContext wac;

  @InjectMocks
  private StudentController studentController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

  }

  @Test
  public void studentGradeWithLessThanCurrentYear_Test() throws Exception {
    MvcResult responseEntity = mockMvc
        .perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)
            .queryParam(INPUT_PARAM_KEY, studentGradeWithLessThanCurrentYear_Test_Input.get()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    assertNotNull(responseEntity.getResponse());
    assertEquals(studentGradeWithLessThanCurrentYear_Test_ExpectedOutput.get(),
        Integer.valueOf(responseEntity.getResponse().getContentAsString()));
  }

  @Test
  public void studentGradeWithGreaterThanCurrentYear_Test() throws Exception {
    MvcResult responseEntity = mockMvc
        .perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)
            .queryParam(INPUT_PARAM_KEY, studentGradeWithGreaterThanCurrentYear_Test_Input.get()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    assertNotNull(responseEntity.getResponse());
    assertEquals(studentGradeWithGreaterThanCurrentYear_Test_ExpectedOutput.get(),
        Integer.valueOf(responseEntity.getResponse().getContentAsString()));
  }

  @Test
  public void studentGradeIfCurrentDateIsLowerThanYearOfGraduation_Test() throws Exception {
    MvcResult responseEntity = mockMvc
        .perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON).queryParam(
            INPUT_PARAM_KEY, studentGradeIfCurrentDateIsLowerThanYearOfGraduation_Test_Input.get()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    assertNotNull(responseEntity.getResponse());
    assertEquals(studentGradeIfCurrentDateIsLowerThanYearOfGraduation_Test_ExpectedOutput.get(),
        Integer.valueOf(responseEntity.getResponse().getContentAsString()));
  }

  @Test
  public void studentGradeWithZeroInput_Test() throws Exception {
    try {
      mockMvc
          .perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON)
              .queryParam(INPUT_PARAM_KEY, studentGradeWithZeroInput_Test_Input.get()))
          .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    } catch (Exception e) {
      CustomStudentAPIException apiException = (CustomStudentAPIException) e.getCause();
      assertEquals(studentGradeWithZeroInput_Test_ExpectedExceptionCode.get(),
          apiException.getStatusCode());
      assertEquals(ExceptionConstants.EMPTY_OR_ZERO_MSG, apiException.getLocalizedMessage());
    }
  }

  private final Supplier<Integer> studentGradeWithGreaterThanCurrentYear_Test_ExpectedOutput =
      () -> 9;
  private final Supplier<Integer> studentGradeWithLessThanCurrentYear_Test_ExpectedOutput =
      () -> 99;
  private final Supplier<Integer> studentGradeIfCurrentDateIsLowerThanYearOfGraduation_Test_ExpectedOutput =
      () -> 0;

  private final Supplier<Integer> studentGradeWithZeroInput_Test_ExpectedExceptionCode = () -> 601;
  private final Supplier<String> studentGradeWithZeroInput_Test_Input = () -> "0";

  private final Supplier<String> studentGradeWithGreaterThanCurrentYear_Test_Input = () -> "2025";
  private final Supplier<String> studentGradeWithLessThanCurrentYear_Test_Input = () -> "2000";
  private final Supplier<String> studentGradeIfCurrentDateIsLowerThanYearOfGraduation_Test_Input =
      () -> "2035";
}
