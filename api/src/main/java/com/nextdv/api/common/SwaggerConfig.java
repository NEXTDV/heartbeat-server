package com.nextdv.api.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI(@Value("${app.swagger-server-url:}") String serverUrl) {
    if (serverUrl.isBlank()) {
      return new OpenAPI();
    }
    return new OpenAPI().servers(List.of(new Server().url(serverUrl)));
  }
}
