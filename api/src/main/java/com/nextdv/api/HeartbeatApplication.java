package com.nextdv.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nextdv")
public class HeartbeatApplication {

  public static void main(String[] args) {
    SpringApplication.run(
        HeartbeatApplication.class,
        args
    );
  }
}
