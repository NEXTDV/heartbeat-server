package com.nextdv.api.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 클래스명: SwaggerConfig
 * 작성자: JBumLee
 *
 * Swagger UI 서버 URL 설정 (Traefik/Cloudflare 환경에서 https 강제)
 */
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
