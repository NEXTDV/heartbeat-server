package com.nextdv.domain.common;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import java.util.UUID;

public final class UuidV7 {

  private static final TimeBasedEpochGenerator GENERATOR = Generators.timeBasedEpochGenerator();

  private UuidV7() {
  }

  public static UUID generate() {
    return GENERATOR.generate();
  }
}
