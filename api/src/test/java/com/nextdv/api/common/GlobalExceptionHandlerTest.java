package com.nextdv.api.common;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

class GlobalExceptionHandlerTest {

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(new StubController())
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  void NoSuchElementException이_발생하면_404를_반환한다() throws Exception {
    mockMvc.perform(get("/stub/not-found"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("존재하지 않습니다"));
  }

  @Test
  void 일반_Exception이_발생하면_500을_반환한다() throws Exception {
    mockMvc.perform(get("/stub/error"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.message").value("서버 오류"));
  }

  @RestController
  static class StubController {

    @GetMapping("/stub/not-found")
    public void notFound() {
      throw new NoSuchElementException("존재하지 않습니다");
    }

    @GetMapping("/stub/error")
    public void error() throws Exception {
      throw new Exception("서버 오류");
    }
  }
}
