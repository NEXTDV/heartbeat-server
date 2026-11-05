package com.nextdv.infrastructure.workflow;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.yaml.snakeyaml.Yaml;

class PrReadyWorkflowTest {

  private static final String GENERAL_WEBHOOK = "https://example.invalid/general";
  private static final String REVIEW_WEBHOOK = "https://example.invalid/review";
  private static final String TITLE = "배포 준비 & review";
  private static final String URL = "https://github.com/NEXTDV/heartbeat-server/pull/126";

  @TempDir Path tempDir;

  @Test
  void ready_전환에서만_알림_작업을_실행한다() throws IOException {
    Map<?, ?> workflow = workflow();
    // SnakeYAML treats the unquoted YAML 1.1 key "on" as a boolean.
    Map<?, ?> triggers = map(workflow.containsKey("on") ? workflow.get("on") : workflow.get(true));
    assertThat(triggers.keySet()).isEqualTo(Set.of("pull_request"));
    assertThat(map(triggers.get("pull_request")).get("types"))
        .isEqualTo(List.of("ready_for_review"));

    Map<?, ?> jobs = map(workflow.get("jobs"));
    assertThat(jobs.keySet()).isEqualTo(Set.of("notify"));
    Map<?, ?> job = map(jobs.get("notify"));
    assertThat(job.containsKey("if")).isFalse();
    assertThat(job.containsKey("permissions")).isFalse();
    List<?> steps = (List<?>) job.get("steps");
    assertThat(steps).hasSize(1);
    Map<?, ?> notification = map(steps.get(0));
    assertThat(notification.containsKey("if")).isFalse();
    assertThat(notification.containsKey("uses")).isFalse();
    Map<?, ?> env = map(notification.get("env"));
    assertThat(env.get("TITLE")).isEqualTo("${{ github.event.pull_request.title }}");
    assertThat(env.get("URL")).isEqualTo("${{ github.event.pull_request.html_url }}");
    assertThat(env.get("DISCORD_WEBHOOK_URL")).isEqualTo("${{ secrets.DISCORD_WEBHOOK_URL }}");
    assertThat(env.get("DISCORD_REVIEW_WEBHOOK_URL"))
        .isEqualTo("${{ secrets.DISCORD_REVIEW_WEBHOOK_URL }}");
  }

  @Test
  void 기본_코드_소유자_파일이_없다() {
    Path root = repositoryRoot();
    for (String path : List.of("CODEOWNERS", ".github/CODEOWNERS", "docs/CODEOWNERS")) {
      assertThat(root.resolve(path)).doesNotExist();
    }
  }

  @Test
  void 두_웹훅에_같은_PR_알림을_보낸다() throws Exception {
    Result result = runNotification("");

    assertThat(result.exitCode()).isZero();
    assertThat(result.arguments()).hasSize(12);
    assertRequest(result.arguments().subList(0, 6), GENERAL_WEBHOOK);
    assertRequest(result.arguments().subList(6, 12), REVIEW_WEBHOOK);
  }

  @Test
  void 첫_웹훅이_실패하면_작업이_실패하고_두번째_요청은_보내지_않는다() throws Exception {
    Result result = runNotification(GENERAL_WEBHOOK);

    assertThat(result.exitCode()).isEqualTo(22);
    assertThat(result.arguments()).hasSize(6);
    assertRequest(result.arguments(), GENERAL_WEBHOOK);
  }

  @Test
  void 두번째_웹훅이_실패하면_작업이_실패한다() throws Exception {
    Result result = runNotification(REVIEW_WEBHOOK);

    assertThat(result.exitCode()).isEqualTo(22);
    assertThat(result.arguments()).hasSize(12);
    assertRequest(result.arguments().subList(0, 6), GENERAL_WEBHOOK);
    assertRequest(result.arguments().subList(6, 12), REVIEW_WEBHOOK);
  }

  private void assertRequest(List<String> arguments, String webhook) throws IOException {
    assertThat(arguments.subList(0, 4))
        .containsExactly("--fail", "-H", "Content-Type: application/json", "-d");
    assertThat(arguments.get(5)).isEqualTo(webhook);
    assertThat(new ObjectMapper().readTree(arguments.get(4)).get("content").asText())
        .isEqualTo("**[PR 리뷰 요청]** " + TITLE + "\n" + URL);
  }

  private Result runNotification(String failingWebhook) throws Exception {
    Map<?, ?> jobs = map(workflow().get("jobs"));
    Map<?, ?> job = map(jobs.get("notify"));
    Map<?, ?> step = map(((List<?>) job.get("steps")).get(0));
    Path calls = tempDir.resolve("calls");
    Path curl = tempDir.resolve("curl");
    Files.writeString(
        curl,
        "#!/bin/bash\n"
            + "printf '%s\\0' \"$@\" >> \"$CALLS\"\n"
            + "if [ \"${!#}\" = \"$FAIL_ON\" ]; then exit 22; fi\n");
    assertThat(curl.toFile().setExecutable(true)).isTrue();

    ProcessBuilder builder =
        new ProcessBuilder("bash", "--noprofile", "--norc", "-e", "-o", "pipefail", "-c",
            (String) step.get("run"));
    builder.redirectErrorStream(true);
    builder.environment().put("PATH", tempDir + ":" + builder.environment().get("PATH"));
    builder.environment().put("CALLS", calls.toString());
    builder.environment().put("FAIL_ON", failingWebhook);
    builder.environment().put("TITLE", TITLE);
    builder.environment().put("URL", URL);
    builder.environment().put("DISCORD_WEBHOOK_URL", GENERAL_WEBHOOK);
    builder.environment().put("DISCORD_REVIEW_WEBHOOK_URL", REVIEW_WEBHOOK);
    Process process = builder.start();
    if (!process.waitFor(5, TimeUnit.SECONDS)) {
      process.destroyForcibly();
      throw new AssertionError("Notification script did not finish");
    }
    String output = Files.readString(calls, StandardCharsets.UTF_8);
    List<String> arguments = Arrays.asList(output.substring(0, output.length() - 1).split("\0", -1));
    return new Result(process.exitValue(), arguments);
  }

  private Map<?, ?> workflow() throws IOException {
    return map(new Yaml().load(Files.readString(repositoryRoot().resolve(".github/workflows/pr-ready.yml"))));
  }

  private static Map<?, ?> map(Object value) {
    assertThat(value).isInstanceOf(Map.class);
    return (Map<?, ?>) value;
  }

  private static Path repositoryRoot() {
    Path current = Path.of("").toAbsolutePath();
    while (current != null && !Files.exists(current.resolve("settings.gradle"))) {
      current = current.getParent();
    }
    assertThat(current).as("repository root").isNotNull();
    return current;
  }

  private record Result(int exitCode, List<String> arguments) {}
}
