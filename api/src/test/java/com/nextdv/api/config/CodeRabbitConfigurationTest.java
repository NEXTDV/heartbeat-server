package com.nextdv.api.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

class CodeRabbitConfigurationTest {

  private Object configuration;

  @BeforeEach
  void setUp() throws IOException {
    LoaderOptions options = new LoaderOptions();
    options.setAllowDuplicateKeys(false);

    // Gradle 테스트 작업의 작업 디렉터리는 api 모듈이다.
    try (Reader reader = Files.newBufferedReader(
        Path.of(
            "..",
            ".coderabbit.yaml"
        )
    )) {
      configuration = new Yaml(new SafeConstructor(options)).load(reader);
    }
  }

  @Test
  void 설정은_중복_키가_없는_단일_YAML_매핑이다() {
    assertThat(configuration).isInstanceOf(Map.class);
  }

  @Test
  void 리뷰_출력_언어는_한국어이다() {
    Map<?, ?> root = mapping(
        configuration,
        "CodeRabbit 설정"
    );

    assertThat(root.get("language")).isEqualTo("ko-KR");
  }

  @Test
  void 일반_PR의_자동_리뷰를_활성화한다() {
    assertThat(autoReview().get("enabled")).isEqualTo(Boolean.TRUE);
  }

  @Test
  void Draft_PR의_자동_리뷰를_비활성화한다() {
    assertThat(autoReview().get("drafts")).isEqualTo(Boolean.FALSE);
  }

  private Map<?, ?> autoReview() {
    Map<?, ?> root = mapping(
        configuration,
        "CodeRabbit 설정"
    );
    Map<?, ?> reviews = mapping(
        root.get("reviews"),
        "reviews"
    );
    return mapping(
        reviews.get("auto_review"),
        "reviews.auto_review"
    );
  }

  private Map<?, ?> mapping(Object value, String path) {
    assertThat(value).as(path).isInstanceOf(Map.class);
    return (Map<?, ?>) value;
  }
}
