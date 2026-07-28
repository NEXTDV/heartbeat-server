package com.nextdv.infrastructure.platform;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.nextdv.domain.platform.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlatformSeeder implements ApplicationRunner {

  private static final TimeBasedEpochGenerator UUID_V7 = Generators.timeBasedEpochGenerator();

  private final PlatformJpaRepository platformJpaRepository;

  private record SeedData(
      String name,
      ServiceCategory category,
      String healthCheckUrl,
      int timeoutMs,
      int degradedThresholdMs,
      String iconUrl,
      boolean isActive) {
  }

  private static final SeedData[] SEEDS = {
      new SeedData(
          "GitHub", ServiceCategory.DEVTOOL, "https://api.github.com", 3000, 1000, null, true
      ),
      new SeedData(
          "Claude",
          ServiceCategory.AI,
          "https://api.anthropic.com/v1/models",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "ChatGPT",
          ServiceCategory.AI,
          "https://api.openai.com/v1/models",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Slack",
          ServiceCategory.COMMUNICATION,
          "https://slack.com/api/api.test",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Discord",
          ServiceCategory.COMMUNICATION,
          "https://discord.com/api/v10/gateway",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Notion",
          ServiceCategory.DEVTOOL,
          "https://api.notion.com/v1/users",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Cloudflare",
          ServiceCategory.CLOUD,
          "https://api.cloudflare.com/client/v4/ips",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Azure",
          ServiceCategory.CLOUD,
          "https://management.azure.com/",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Datadog",
          ServiceCategory.DEVTOOL,
          "https://api.datadoghq.com/api/v1/validate",
          3000,
          1000,
          null,
          true
      ),
      new SeedData(
          "Gemini",
          ServiceCategory.AI,
          "https://generativelanguage.googleapis.com/",
          3000,
          1000,
          null,
          false
      ),
      new SeedData(
          "AWS", ServiceCategory.CLOUD, "https://ec2.amazonaws.com/", 3000, 1000, null, false
      ),
      new SeedData(
          "GCP",
          ServiceCategory.CLOUD,
          "https://www.googleapis.com/discovery/v1/apis",
          3000,
          1000,
          null,
          false
      ),
      new SeedData(
          "Vercel", ServiceCategory.CLOUD, "https://api.vercel.com/", 3000, 1000, null, false
      ),
      new SeedData(
          "Jira",
          ServiceCategory.DEVTOOL,
          "https://jira.atlassian.com/rest/api/2/serverInfo",
          3000,
          1000,
          null,
          false
      ),
  };

  @Override
  public void run(ApplicationArguments args) {
    for (SeedData seed : SEEDS) {
      if (platformJpaRepository.existsByName(seed.name())) {
        continue;
      }
      platformJpaRepository.save(
          new PlatformEntity(
              UUID_V7.generate(),
              seed.name(),
              seed.category(),
              seed.healthCheckUrl(),
              seed.timeoutMs(),
              seed.degradedThresholdMs(),
              seed.iconUrl(),
              seed.isActive()
          )
      );
    }
  }
}
