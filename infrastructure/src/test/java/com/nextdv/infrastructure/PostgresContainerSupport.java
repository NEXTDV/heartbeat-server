package com.nextdv.infrastructure;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;

// 모든 서비스 테스트가 함께 쓰는 PostgreSQL 컨테이너.
// 테스트 클래스마다 켜고 끄면 캐시된 스프링 컨텍스트가 JVM 종료 시 꺼진 DB에 연결하다 30초씩 기다리므로,
// 한 번만 켜고 JVM이 끝날 때까지 유지한다 (종료 후 정리는 Testcontainers Ryuk가 맡는다).
public abstract class PostgresContainerSupport {

  @ServiceConnection
  protected static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  static {
    POSTGRES.start();
  }
}
