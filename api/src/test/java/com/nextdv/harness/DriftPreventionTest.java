package com.nextdv.harness;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.nextdv")
class DriftPreventionTest {

  @ArchTest
  static final ArchRule noTempClasses =
      noClasses()
          .should()
          .haveSimpleNameContaining("Temp")
          .orShould()
          .haveSimpleNameContaining("Old")
          .orShould()
          .haveSimpleNameContaining("Bak")
          .because("임시 클래스는 AGENTS.md 규칙에 따라 금지됩니다.");
}
