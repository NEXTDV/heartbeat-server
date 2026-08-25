package com.nextdv.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

/**
 * 클래스명: SchedulingConfig
 * 작성자: JBumLee
 *
 * 스케줄링 활성화 및 헬스체크용 RestClient 빈 등록 설정
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

  @Bean
  RestClient healthCheckRestClient() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(5000);
    factory.setReadTimeout(10000);
    return RestClient.builder().requestFactory(factory).build();
  }
}
