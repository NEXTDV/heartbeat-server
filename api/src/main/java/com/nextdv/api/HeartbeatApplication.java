package com.nextdv.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * class: HeartbeatApplication 작성자: JBumLee
 *
 * Spring Boot 애플리케이션 진입점
 */
@SpringBootApplication(scanBasePackages = "com.nextdv")
public class HeartbeatApplication {

  public static void main(String[] args) {
    SpringApplication.run(
        HeartbeatApplication.class,
        args
    );
  }
}
